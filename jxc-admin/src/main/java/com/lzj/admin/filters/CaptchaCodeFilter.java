package com.lzj.admin.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzj.admin.model.CaptchaImageModel;
import com.lzj.admin.model.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
@Component
public class CaptchaCodeFilter extends OncePerRequestFilter {

    private static ObjectMapper objectMapper = new ObjectMapper(); //向客户端相应内容

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 只有在登录请求是才有验证码过滤操作
        if (StringUtils.equals("/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            // 校验登录验证码是否正确
            try {
                this.validate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(
                        RespBean.error("验证码错误")));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        HttpSession session = request.getRequest().getSession();

        // 获取请求中参数值
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "captchaCode");

        if (StringUtils.isEmpty(codeInRequest)) {
            throw new SessionAuthenticationException("验证码不能为空!");
        }

        CaptchaImageModel codeInSession = (CaptchaImageModel) session.getAttribute("captcha_key");

        //验证码不存在
        if (Objects.isNull(codeInSession)) {
            throw new SessionAuthenticationException("验证码不存在!");
        }

        //判断会话中的对象是否过期
        if (codeInSession.isExpired()) {
            throw new SessionAuthenticationException("验证码已过期!");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new SessionAuthenticationException("验证码不匹配!");
        }

    }
}
