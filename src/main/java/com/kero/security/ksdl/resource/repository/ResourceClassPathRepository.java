package com.kero.security.ksdl.resource.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.kero.security.ksdl.resource.FileResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public abstract class ResourceClassPathRepository<T> implements KsdlResourceRepository<FileResource<T>>{
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");

	private String[] ksdlFileSuffixes;
	
	public ResourceClassPathRepository(String[] suffixes) {
		
		this.ksdlFileSuffixes = suffixes;
	}
	

	protected abstract FileResource<T> getResource(Path repositoryPath, Path path);
	
	@Override
	public FileResource<T> getResource(ResourceAddress address) {
		
		return getResource(Paths.get(adaptAddress(address)));
	}
	
	private FileResource<T> getResource(Path path) {
		
		String[] classPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
	
		for(String suspect : classPaths) {
			
			if(path.startsWith(suspect)) {
				
				Path repositoryPath = Paths.get(suspect);
				
				return getResource(repositoryPath, path);
			}
		}
		
		throw new RuntimeException("Can't determinate repository path!");
	}
	
	@Override
	public Collection<FileResource<T>> getAll() {
		
		Collection<FileResource<T>> result = new HashSet<>();
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		int totalFilesFound = 0;
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:**/*"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:**/*"+suffix);
			
				totalFilesFound += resources.length;
				
				for(Resource resource : resources) {
					
					FileResource<T> ksdlResource = getResource(resource.getFile().toPath());
					
					LOGGER.debug("Found KSDL file resource: "+adaptAddress(ksdlResource.getAddress()));
					
					result.add(ksdlResource);
				}
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}
	
		LOGGER.info("Found KSDL files in classpath: "+totalFilesFound);
		
		return result;
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
