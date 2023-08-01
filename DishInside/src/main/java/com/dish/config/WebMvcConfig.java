package com.dish.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dish.interceptor.LoggerInterceptor;
import com.dish.interceptor.LoginCheckInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private String connectPath = "/imagePath/**"; 
	@Value("${uploadPath}")
	private String uploadPath;
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(connectPath)
			.addResourceLocations(uploadPath);
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**","/image/**", "/js/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**/*.do")
                .excludePathPatterns("/log*","/post/main.do");
    }
    
	
}