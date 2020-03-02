package com.moneymanager.moneymanager;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class MoneymanagerApplication extends SpringBootServletInitializer {

//	@Autowired
//	RegistrationService registrationService;

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<MoneymanagerApplication> applicationClass = MoneymanagerApplication.class;

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			/*
			 * @Override public void addCorsMappings(CorsRegistry registry) {
			 * registry.addMapping("/**").allowedMethods("GET", "POST", "PUT",
			 * "DELETE").allowedOrigins("http://localhost:4200") .allowedHeaders("*"); }
			 */
		};
	}
	
	
	  @Bean public CorsFilter corsFilter() { final UrlBasedCorsConfigurationSource
	  source = new UrlBasedCorsConfigurationSource(); final CorsConfiguration
	  config = new CorsConfiguration(); config.setAllowCredentials(true);
	  config.setAllowedOrigins(Collections.singletonList("*"));
	  config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
	  config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS",
	  "DELETE", "PATCH")); source.registerCorsConfiguration("/**", config); return
	  new CorsFilter(source); }
	 
}
