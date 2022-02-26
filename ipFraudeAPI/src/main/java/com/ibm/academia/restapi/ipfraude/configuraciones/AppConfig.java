package com.ibm.academia.restapi.ipfraude.configuraciones;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> circuitCustomizer(){
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					
					.build();
		});
	}
}