package com.kero.security.spring.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgentFactory;
import com.kero.security.core.agent.KeroAccessAgentFactoryImpl;

@Configuration
public class KeroAccessAgentFactoryBean {
	
	@Autowired
	private List<KeroAccessAgentFactorySpringConfiguration> springConfigurators;
	
	@Bean
	public KeroAccessAgentFactory keroAccessAgentFactory() {
		
		KeroAccessAgentFactory factory = new KeroAccessAgentFactoryImpl();
	
		for(KeroAccessAgentFactorySpringConfiguration configuration : springConfigurators) {
			
			configuration.configure(factory);
		}
		
		return factory;
	}
}