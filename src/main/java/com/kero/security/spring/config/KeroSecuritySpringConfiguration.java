package com.kero.security.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.managers.KeroAccessManager;
import com.kero.security.core.managers.KeroAccessManagerImpl;

@Configuration
public class KeroSecuritySpringConfiguration {

	@Bean
	public KeroAccessManager keroAccessManager() {
		
		return new KeroAccessManagerImpl();
	}
}