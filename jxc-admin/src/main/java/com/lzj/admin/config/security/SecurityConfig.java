package com.lzj.admin.config.security;

import com.lzj.admin.config.ClassPathTldsLoader;
import com.lzj.admin.filters.CaptchaCodeFilter;
import com.lzj.admin.pojo.User;
import com.lzj.admin.service.IRbacService;
import com.lzj.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 乐字节  踏实教育 用心服务
 *
 * @author 乐字节--老李
 * @version 1.0
 */
@SpringBootConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    private JxcAuthenticationSuccessHandler jxcAuthenticationSuccessHandler;


    @Autowired
    private JxcAuthenticationFailedHandler jxcAuthenticationFailedHandler;

    @Resource
    private IUserService userService;

    @Resource
    private  JxcLogoutSuccessHandler jxcLogoutSuccessHandler;

    @Resource
    private CaptchaCodeFilter captchaCodeFilter;

    @Resource
    private DataSource dataSource;

    @Resource
    private IRbacService rbacService;

    /**
     * 放行静态资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/images/**",
                "/css/**",
                "/js/**",
                "/lib/**",
                "/error/**");
    }


    //设置springSecurity
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable()
            .addFilterBefore(captchaCodeFilter, UsernamePasswordAuthenticationFilter.class) //过滤验证码的过滤器
            // 允许iframe 页面嵌套
            .headers().frameOptions().disable()
            .and()
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("password")
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .successHandler(jxcAuthenticationSuccessHandler)
                .failureHandler(jxcAuthenticationFailedHandler)
            .and()
                .logout()
                .logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(jxcLogoutSuccessHandler)
            .and()
                .rememberMe()
                .rememberMeParameter("rememberMe")
                //保存在浏览器端的cookie的名称，如果不设置默认也是remember-me
                .rememberMeCookieName("remember-me-cookie")
                //设置token的有效期，即多长时间内可以免除重复登录，单位是秒。
                .tokenValiditySeconds(7  * 24 * 60 * 60)
                //自定义
                .tokenRepository(persistentTokenRepository())
            .and()
                  .authorizeRequests().antMatchers("/index","/login","/image").permitAll()
                  .anyRequest().authenticated();
    }

    /**
     * 配置从数据库中获取token
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }



    @Bean
    public UserDetailsService userDetailsService(){ //交给权限框架,查询用户
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User userDetails = userService.findUserByUserName(username);

                /**
                 * 1.查询用户分配的角色
                 * 2.根据用户扮演的角色查询角色拥有的权限记录
                 **/
                List<String> roleNames = rbacService.findRolesByUserName(username);
                List<String> authorities = rbacService.findAuthoritiesByRoleName(roleNames);

                roleNames = roleNames.stream().map(role-> "ROLE_"+role).collect(Collectors.toList()); //取出role的每一项
                authorities.addAll(roleNames);

                userDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));
                return userDetails;
            }
        };
    }


    @Bean
    public PasswordEncoder encoder(){ //对密码加密
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //告诉权限框架，用上面的UserDetailsService
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }


    /**
     * 加载 ClassPathTldsLoader
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader(){
        return new ClassPathTldsLoader();
    }
}
