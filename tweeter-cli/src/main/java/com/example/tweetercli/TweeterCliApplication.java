package com.example.tweetercli;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootApplication
public class TweeterCliApplication implements CommandLineRunner {
	@Autowired
	private CliConfigurationProperties cliConfig;

	public static void main(String[] args) {
		SpringApplication.run(TweeterCliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		String userName = readInputIfNull(cliConfig.getUsername(), "Enter username : ");
		String password = readInputIfNull(cliConfig.getPassword(), "Enter password : ");
		String text = readInputIfNull(cliConfig.getText(), "Enter text : ");
		
		// Get Access Token
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "password");
		body.add("username", userName);
		body.add("password", password);
		body.add("scope", "tweet.read tweet.write");
		
		JsonNode token = restTemplate.exchange(
				RequestEntity.post(cliConfig.getAccessTokenUri())
					.header("Authorization", basicAuthHeaderValue())
					.body(body), JsonNode.class)
				.getBody();
				
		String accessToken = token.get("access_token").asText();
		
		if(!StringUtils.isEmpty(text)) {
			URI tweetsUri = UriComponentsBuilder
							.fromUri(cliConfig.getTweetApiUri())
							.pathSegment("tweets")
							.build()
							.toUri();
			Map<String, String> tweetBody = Collections.singletonMap("text", text);

			restTemplate.exchange(
					RequestEntity.post(tweetsUri)
						.header("Authorization", accessTokenValue(accessToken))
						.body(tweetBody), JsonNode.class);
		}
		
		URI timelinesUri = UriComponentsBuilder
				.fromUri(cliConfig.getTweetApiUri())
				.pathSegment("timelines")
				.build()
				.toUri();
		
		JsonNode timelines = restTemplate.exchange(
				RequestEntity.get(timelinesUri)
					.header("Authorization", accessTokenValue(accessToken))
					.build() , JsonNode.class)
				.getBody();
		timelines.forEach(tweet -> {
			System.out.println("=========");
			System.out.println("Name: " + tweet.get("username").asText());
			System.out.println("Text: " + tweet.get("text").asText());
			System.out.println("Date: " + tweet.get("createdAt").asText());
		});
	}
	
	private String readInputIfNull(String strVal, String prompt) {
		if(strVal != null) {
			return strVal;
		} else {
			System.out.print(prompt);
			return System.console().readLine();	
		}
	}

	private String basicAuthHeaderValue() {
		String userPassword = cliConfig.getClientId() + ":" + cliConfig.getClientSecret();
		return "Basic " + Base64Utils.encodeToString((userPassword).getBytes());
	}
	
	private String accessTokenValue(String accessToken) {
		return "Bearer " + accessToken;
	}
	
	@Component
	@ConfigurationProperties(prefix = "cli")
	public static class CliConfigurationProperties {
		private URI accessTokenUri;
		private String clientId;
		private String clientSecret;
		private URI tweetApiUri;
		private String username;
		private String password;
		private String text;
		
		public URI getAccessTokenUri() {
			return accessTokenUri;
		}
		public void setAccessTokenUri(URI accessTokenUri) {
			this.accessTokenUri = accessTokenUri;
		}
		public String getClientId() {
			return clientId;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public String getClientSecret() {
			return clientSecret;
		}
		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}
		public URI getTweetApiUri() {
			return tweetApiUri;
		}
		public void setTweetApiUri(URI tweetApiUri) {
			this.tweetApiUri = tweetApiUri;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
}

