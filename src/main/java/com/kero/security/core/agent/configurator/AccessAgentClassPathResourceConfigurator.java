package com.kero.security.core.agent.configurator;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.kero.security.ksdl.agent.KsdlAgent;
import com.kero.security.ksdl.agent.KsdlAgentFactory;
import com.kero.security.ksdl.agent.configuration.KsdlAgentConfigurator;
import com.kero.security.ksdl.provider.resource.FileResource;
import com.kero.security.ksdl.provider.resource.KsdlTextResource;
import com.kero.security.spring.config.KsdlAgentFactorySpringConfiguration;

@Component
public class AccessAgentClassPathResourceConfigurator implements KsdlAgentConfigurator, KsdlAgentFactorySpringConfiguration {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");

	@Value("${kero.security.lang.file.suffixes:**/*.k-s,**/*.ks}")
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
	
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		int totalFilesFound = 0;
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:"+suffix);
			
				totalFilesFound += resources.length;
				
				for(Resource resource : resources) {
			
					KsdlTextResource ksdlResource = new FileResource(resource.getFile().toPath());
					
					LOGGER.debug("Found KSDL file resource: "+resource.getFilename());

					agent.addTextResource(ksdlResource);
				}
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}
	
		LOGGER.info("Found KSDL files in classpath: "+totalFilesFound);
	}
}
