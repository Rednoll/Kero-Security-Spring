package com.kero.security.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.ksdl.agent.KsdlAgentFactory;
import com.kero.security.ksdl.agent.KsdlAgentFactoryImpl;

@Configuration
public class KsdlAgentFactoryBean {
	
	@Autowired(required = false)
	private List<KsdlAgentFactorySpringConfiguration> springConfigurators = new ArrayList<>();
	
	@Bean
	public KsdlAgentFactory ksdlAgentFactory() {
		
		KsdlAgentFactory factory = new KsdlAgentFactoryImpl();
	
		for(KsdlAgentFactorySpringConfiguration configuration : springConfigurators) {
			
			configuration.configure(factory);
		}
		
		return factory;
	}
}