package com.kero.security.ksdl.resource;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public abstract class ClassPathResource<T> implements KsdlResource<T> {

	protected Resource resource;
	
	public ClassPathResource(Resource resource) {
	
		this.resource = resource;
	}
	
	@Override
	public ResourceAddress getAddress() {

		try {

			String resourcePath = resource.getURL().toString();
			
			String[] rawPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
			
			for(String rawPath : rawPaths) {
				
				String classPath = Paths.get(rawPath).toUri().toURL().toString();
				
				if(resourcePath.contains(rawPath) || resourcePath.contains(classPath)) {
					
					String address = resourcePath;	
						address = address.replaceAll("^.*"+Pattern.quote(classPath), "");
						address = address.replaceAll("^.*"+Pattern.quote(rawPath), "");
						address = address.replaceFirst("\\..+$", "");
						
						if(address.startsWith("!")) {
							
							address = address.substring(1);
						}

						address = address.replaceAll("\\/", ResourceAddress.SEPARATOR);
						
						if(address.startsWith(".")) {
							
							address = address.substring(1);
						}
						
					return new ResourceAddress(address);
				}
			}

			throw new RuntimeException("Can't determine classpath for: "+resourcePath);			
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}
