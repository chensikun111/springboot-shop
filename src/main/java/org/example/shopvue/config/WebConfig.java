package org.example.shopvue.config;

import org.example.shopvue.interceptors.JWTInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptors()).
                addPathPatterns("/**")//所有接口进行拦截
                .excludePathPatterns("/user/login", "/user/register",
                        "/admin/login", "/shoplist/**", "/category",
                        "/discount", "/search/**", "/bannerimage",
                        "/review/shopid/**", "/swagger-resources/**",
                        "/webjars/**", "/v2/**", "/swagger-ui.html/**","/alipay/**","/emailCode/**")
                .excludePathPatterns("/captcha/**")//放行图片
                .excludePathPatterns("/img/**");
    }


}
