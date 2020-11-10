package com.kero.security.ksdl.resource.repository;

import java.nio.file.Path;

import com.kero.security.ksdl.resource.FileResource;
import com.kero.security.ksdl.resource.FileTextResource;

public class TextResourceClassPathRepository extends ResourceClassPathRepository<String> {

	public TextResourceClassPathRepository(String[] suffixes) {
		super(suffixes);
	
	}

	@Override
	protected FileResource<String> getResource(Path repositoryPath, Path path) {
		
		return new FileTextResource(repositoryPath, path);
	}
}
