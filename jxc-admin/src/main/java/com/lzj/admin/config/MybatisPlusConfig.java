package com.lzj.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
@SpringBootConfiguration
public class MybatisPlusConfig {


    @Bean
    public MybatisPlusInterceptor paginationInnerInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor =new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

}
