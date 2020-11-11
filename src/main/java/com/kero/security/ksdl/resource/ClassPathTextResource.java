package com.kero.security.ksdl.resource;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

public class ClassPathTextResource extends ClassPathResource<String> {

	public ClassPathTextResource(Resource resource) {
		super(resource);
	
	}

	@Override
	public String readData() {
		
		try {
			
			return IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
		}
		catch(IOException e) {
		
			throw new RuntimeException(e);
		}
	}
}