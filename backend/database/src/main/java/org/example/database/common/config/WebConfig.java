package org.example.database.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 用于注册JWT拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        // 排除登录相关接口
                        "/web/login",
                        "/web/loginBasic",
                        "/web/register",
                        "/web/logout",
                        // 排除静态资源
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        // 排除Swagger文档（如果使用）
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        // 排除健康检查
                        "/actuator/**",
                        // 排除错误页面
                        "/error"
                );
    }
}
