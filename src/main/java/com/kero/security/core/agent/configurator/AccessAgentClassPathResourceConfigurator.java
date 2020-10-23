package com.kero.security.core.agent.configurator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.KeroAccessAgentFactory;
import com.kero.security.core.agent.configuration.KeroAccessAgentConfigurator;
import com.kero.security.lang.provider.BaseCompositeProvider;
import com.kero.security.lang.provider.KsdlProvider;
import com.kero.security.lang.provider.TextualProvider;
import com.kero.security.lang.provider.resource.FileResource;
import com.kero.security.lang.provider.resource.KsdlTextResource;
import com.kero.security.spring.config.KeroAccessAgentFactorySpringConfiguration;

@Component
public class AccessAgentClassPathResourceConfigurator implements KeroAccessAgentConfigurator, KeroAccessAgentFactorySpringConfiguration {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");
	
	@Value("${kero.security.lang.resource.cache.enabled:true}")
	private boolean resourceCacheEnabled;
	
	@Value("${kero.security.lang.provider.cache.enabled:true}")
	private boolean providerCacheEnabled;

	@Value("${kero.security.lang.file.suffixes:**/*.k-s,**/*.ks}")
	private String[] ksdlFileSuffixes;

	@Value("${kero.security.lang.resource.classpath.enabled:true}")
	private boolean resourceClassPathEnabled;
	
	@Override
	public void configure(KeroAccessAgentFactory factory) {
		
		if(!this.resourceClassPathEnabled) return;
		
		factory.addConfigurator(this);
	}
	
	@Override
	public void configure(KeroAccessAgent agent) {
	
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		Set<KsdlProvider> sources = new HashSet<>();
		
		int totalFilesFound = 0;
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:"+suffix);
			
				totalFilesFound += resources.length;
				
				for(Resource resource : resources) {
			
					KsdlTextResource ksdlResource = new FileResource(resource.getFile().toPath());
					
					if(resourceCacheEnabled) {
						
						ksdlResource = KsdlTextResource.addCacheWrap(ksdlResource);
						LOGGER.debug("Found KSDL file resource: "+resource.getFilename());
					}
					
					sources.add(new TextualProvider(ksdlResource));
				}
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}
	
		LOGGER.info("Found KSDL files in classpath: "+totalFilesFound);
		
		KsdlProvider provider = new BaseCompositeProvider(sources);
		
		if(providerCacheEnabled) {
			
			provider = KsdlProvider.addCacheWrap(provider);
		}
		
		agent.addKsdlProvider(provider);
	}
}
