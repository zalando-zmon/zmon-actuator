/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zalando.zmon.boot.jetty;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRunner {

	private static String LINE = "\"counter.status.200.api.simple.id.complex\":100";

	@Value("${local.server.port}")
	private int port;

	@Test
	public void run() throws InterruptedException {
		RestTemplate rest = new RestTemplate();
		for (int i = 0; i < 100; i++) {
			ResponseEntity<String> response = rest
					.getForEntity("http://localhost:" + port + "/api/simple/" + i + "/complex", String.class);
			Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
		
		ResponseEntity<String> metrics = rest.getForEntity("http://localhost:" + port + "/metrics", String.class); 
		Assertions.assertThat(metrics.getBody()).contains(LINE);
		// to use browser /metrics
//		TimeUnit.MINUTES.sleep(2);
	}
}
