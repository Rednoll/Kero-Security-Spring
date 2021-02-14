package com.kero.security.core.agent.configurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kero.security.ksdl.agent.KsdlAgent;
import com.kero.security.ksdl.agent.KsdlAgentFactory;
import com.kero.security.ksdl.agent.configurator.KsdlAgentConfigurator;
import com.kero.security.ksdl.resource.repository.ResourceClassPathRepository;
import com.kero.security.spring.config.KsdlAgentFactorySpringConfiguration;

@Component
public class AccessAgentClassPathResourceConfigurator implements KsdlAgentConfigurator, KsdlAgentFactorySpringConfiguration {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");

	@Value("${kero.security.lang.file.suffixes:.k-s,.ks}")
	private String[] ksdlFileSuffixes;

	@Value("${kero.security.lang.resource.classpath.enabled:true}")
	private boolean resourceClassPathEnabled;

	@Override
	public void configure(KsdlAgentFactory factory) {
		
		if(!resourceClassPathEnabled) return;
	
		factory.addConfigurator(this);
	}
	
	@Override
	public void configure(KsdlAgent agent) {
	
		ResourceClassPathRepository resource = new ResourceClassPathRepository(ksdlFileSuffixes);
		
		agent.addResource(resource);
	}
}
