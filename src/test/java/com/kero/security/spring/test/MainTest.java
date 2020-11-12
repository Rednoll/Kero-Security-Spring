package com.kero.security.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kero.security.core.agent.configurator.KeroAccessAgentConfiguratorBeans;
import com.kero.security.ksdl.agent.KsdlAgent;
import com.kero.security.spring.config.KeroAccessAgentBean;
import com.kero.security.spring.config.KeroAccessAgentFactoryBean;
import com.kero.security.spring.config.KsdlAgentBean;
import com.kero.security.spring.config.KsdlAgentFactoryBean;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeroAccessAgentBean.class, KeroAccessAgentFactoryBean.class, KsdlAgentBean.class, KsdlAgentFactoryBean.class, KeroAccessAgentConfiguratorBeans.class})
@ActiveProfiles("test")
public class MainTest {
	
	@Autowired
	private KsdlAgent ksdlAgent;
	
	@Test
	public void test() {
		
	}
}
