package com.diagnostic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing()
public class DiagnosticDeliveryServiceApplication {

//	@Bean
//	public AuditorAware<String> auditorAware() {
//		return new AuditorAwareImpl(); auditorAwareRef = "auditorAware"
//	}

	public static void main(String[] args) {

		SpringApplication.run(DiagnosticDeliveryServiceApplication.class, args);
	}

//	@Configuration
//	public class WebConfiguration extends WebMvcConfigurationSupport {
//
//		@Override
//		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//			registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//		}
//	}

//	@Bean
//	WebMvcConfigurer webMvcConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//
//			@Override
//			public void addResourceHandlers(ResourceHandlerRegistry registry) {
//				registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//			}
//		};
//	}

}
