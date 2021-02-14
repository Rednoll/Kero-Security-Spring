package com.kero.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.agent.KsdlAgent;
import com.kero.security.ksdl.agent.KsdlAgentFactory;

@Configuration
public class KsdlAgentBean {

	@Autowired
	private KsdlAgentFactory factory;

	@Autowired
	private KeroAccessAgent accessAgent;
	
	@Bean
	public KsdlAgent ksdlAgent() {
		
		KsdlAgent agent = factory.create(accessAgent);
		
		agent.init();
		
		return agent;
	}
}
