package com.kero.security.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.ksdl.agent.KsdlAgentFactory;
import com.kero.security.ksdl.agent.KsdlAgentFactoryImpl;

@Configuration
public class KsdlAgentFactoryBean {
		
	@Bean
	public KsdlAgentFactory keroAccessAgentFactory() {
		
		return new KsdlAgentFactoryImpl();
	}
}