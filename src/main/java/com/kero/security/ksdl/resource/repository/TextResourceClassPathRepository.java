package com.kero.security.ksdl.resource.repository;

import org.springframework.core.io.Resource;

import com.kero.security.ksdl.resource.ClassPathTextResource;

public class TextResourceClassPathRepository extends ResourceClassPathRepository<String> {

	public TextResourceClassPathRepository() {
		this(new String[] {".k-s", ".ks"});
	
	}
	
	public TextResourceClassPathRepository(String[] suffixes) {
		super(suffixes);
	
	}

	@Override
	protected ClassPathTextResource getResource(Resource resource) {
		
		return new ClassPathTextResource(resource);
	}
}
