package com.course.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
	private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GatewayApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("Gateway地址:\thttp:127.0.0.1:{}",environment.getProperty("server.port"));
	}
	/**
	 * @description: 配置跨域
	 * @author wangzhifang
	 * @createTime：2021/3/11 19:40
	 */
	@Bean
	public CorsWebFilter corsWebFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		//1.配置跨域
		//允许哪种请求头跨域
		corsConfiguration.addAllowedHeader("*");
		//允许哪种方法类型跨域 get post delete put
		corsConfiguration.addAllowedMethod("*");
		// 允许哪些请求源跨域
		corsConfiguration.addAllowedOriginPattern("*");
		//corsConfiguration.addAllowedOrigin("*");此处好坑
		// 是否携带cookie跨域
		corsConfiguration.setAllowCredentials(true);
		//允许跨域的路径
		source.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsWebFilter(source);
	}
}
