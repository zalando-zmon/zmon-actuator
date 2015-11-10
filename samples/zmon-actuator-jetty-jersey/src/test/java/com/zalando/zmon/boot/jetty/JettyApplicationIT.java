package com.zalando.zmon.boot.jetty;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.zalando.zmon.boot.jetty.JettyApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={JettyApplication.class})
@WebIntegrationTest
public class JettyApplicationIT {
	
	@Value("${local.server.port}")
	private int port;

	@Test
	public void run() throws InterruptedException{
		RestTemplate rest = new RestTemplate();
		for(int i = 0; i < 100; i++){
			ResponseEntity<String> response = rest.getForEntity("http://localhost:" + port + "/api/simple/" + i + "/complex", String.class);
		}
		
		TimeUnit.MINUTES.sleep(2);
	}

}
