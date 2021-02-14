package com.kero.security.ksdl.resource.repository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.kero.security.ksdl.resource.KsdlResource;

public class ResourceClassPathRepository implements KsdlResource {
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");

	private String[] ksdlFileSuffixes;
	
	public ResourceClassPathRepository() {
		this(new String[] {".k-s", ".ks"});
	
	}
	
	public ResourceClassPathRepository(String[] suffixes) {
		
		this.ksdlFileSuffixes = suffixes;
	}
	
	@Override
	public String read() {
		
		Set<String> result = new HashSet<>();
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:**/*"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:**/*"+suffix);
			
				for(Resource resource : resources) {
					
					result.add(IOUtils.toString(resource.getInputStream(), Charset.defaultCharset())+"\n");
				}
			}
			catch(IOException e) {

				e.printStackTrace();
			}
		}
		
		return String.join("\n", result);
	}
}
