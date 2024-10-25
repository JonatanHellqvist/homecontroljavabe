package com.homecontroljavabe.homecontroljavabe.openai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiRestTemplate {
	
	@Value("${openai.api.key}")
	private String openAiApiKey;

	@Bean
	@Qualifier("openAiRestTemplate")

	public RestTemplate openaiRestTemplate() {

	    RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add("Authorization", "Bearer " + openAiApiKey);
			return execution.execute(request, body);
		});
		return restTemplate;
	}
}