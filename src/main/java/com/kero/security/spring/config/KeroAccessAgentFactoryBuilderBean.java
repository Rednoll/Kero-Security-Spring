package com.kero.security.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgentFactoryBuilder;
import com.kero.security.core.agent.KeroAccessAgentFactoryImpl;

@Configuration
public class KeroAccessAgentFactoryBuilderBean {

	@Value("${kero.security.agent.main_provider.preloading.enabled:true}")
	private boolean mainProviderPreloading;
	
	@Bean
	public KeroAccessAgentFactoryBuilder keroAccessAgentFactoryBuilder() {
		
		KeroAccessAgentFactoryBuilder builder = new KeroAccessAgentFactoryImpl.Builder();
			builder.setMainProviderPreloading(this.mainProviderPreloading);
		
		return builder;
	}
}
