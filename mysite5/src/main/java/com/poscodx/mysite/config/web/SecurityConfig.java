package com.poscodx.mysite.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer{
	// Argument
//	@Bean
//	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
//		return new AuthUserHandlerMethodArgumentResolver();
//	}

//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//		resolvers.add(handlerMethodArgumentResolver());
//		
//	}
	
//	// Interceptors
//	@Bean
//	public HandlerInterceptor loginInterceptor() {
//		return new LoginInterceptor();
//	}
//	
//	@Bean
//	public HandlerInterceptor logoutInterceptor() {
//		return new LogoutInterceptor();
//	}
//	
//	@Bean
//	public HandlerInterceptor authInterceptor() {
//		return new AuthInterceptor();
//	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/auth");
//		registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout");
//		registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/auth","/user/logout","/assets/**");
//	}
	
	
}
