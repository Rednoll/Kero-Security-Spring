package com.kero.security.spring.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.configurator.KeroAccessAgentConfigruatorBeans;
import com.kero.security.core.exception.AccessException;
import com.kero.security.spring.config.KeroAccessAgentConfiguration;
import com.kero.security.spring.config.KeroAccessAgentFactoryConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeroAccessAgentConfiguration.class, KeroAccessAgentFactoryConfiguration.class, KeroAccessAgentConfigruatorBeans.class})
@ActiveProfiles("test")
public class MainTest {
	
	@Autowired
	private KeroAccessAgent manager;
	
	@Test
	public void test() {
		
		TestObject obj = new TestObject("kek");
	
		TestObject ms = manager.protect(obj, "MS");
		
		assertThrows(AccessException.class, ms::getText);
	
		TestObject owner = manager.protect(obj, "OWNER");
		
		owner.getText();
	}
}
