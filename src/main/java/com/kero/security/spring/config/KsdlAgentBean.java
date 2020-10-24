package com.kero.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${kero.security.ksdl.agent.main_provider.preloading.enabled:true}")
	private boolean mainProviderPreloadingEnabled;
	
	@Bean
	public KsdlAgent ksdlAgent() {
		
		KsdlAgent agent = factory.create(accessAgent);
	
		if(this.mainProviderPreloadingEnabled) {
			
			agent.preloadMainProvider();
		}
		
		return agent;
	}
}
