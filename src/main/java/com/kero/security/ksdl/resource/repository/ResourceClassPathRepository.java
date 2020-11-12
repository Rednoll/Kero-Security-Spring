package com.kero.security.ksdl.resource.repository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.kero.security.ksdl.resource.ClassPathResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public abstract class ResourceClassPathRepository<T> implements KsdlResourceRepository<ClassPathResource<T>>{
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");

	private String[] ksdlFileSuffixes;
	
	public ResourceClassPathRepository() {
		this(new String[] {".k-s", ".ks"});
	
	}
	
	public ResourceClassPathRepository(String[] suffixes) {
		
		this.ksdlFileSuffixes = suffixes;
	}

	protected abstract ClassPathResource<T> getResource(Resource resource);
	
	@Override
	public ClassPathResource<T> getResource(ResourceAddress address) {
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				Resource[] resources = resolver.getResources("classpath*:**/"+adaptAddress(address)+suffix);
			
				if(resources.length > 0) {
					
					return getResource(resources[0]);
				}
			}
			catch(IOException e) {

				e.printStackTrace();
			}
		}
		
		throw new RuntimeException("Can't find resource: "+adaptAddress(address));
	}
	
	@Override
	public Collection<ClassPathResource<T>> getAll() {
		
		Map<ResourceAddress, ClassPathResource<T>> result = new HashMap<>();
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:**/*"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:**/*"+suffix);
			
				for(Resource resource : resources) {
					
					ClassPathResource<T> ksdlResource = getResource(resource);
					ResourceAddress address = ksdlResource.getAddress();
					
					if(!result.containsKey(address)) {
						
						LOGGER.debug("Found KSDL resource: "+adaptAddress(address));
						result.put(address, ksdlResource);
					}
				}
			}
			catch(IOException e) {

				e.printStackTrace();
			}
		}
	
		LOGGER.info("Found KSDL in classpath: "+result.size());
		
		return result.values();
	}
	
	@Override
	public String getName() {
		
		return "ClassPath";
	}

	@Override
	public String adaptAddress(ResourceAddress address) {
	
		return address.getRaw().replaceAll("\\"+ResourceAddress.SEPARATOR, Matcher.quoteReplacement(File.separator));
	}
}
