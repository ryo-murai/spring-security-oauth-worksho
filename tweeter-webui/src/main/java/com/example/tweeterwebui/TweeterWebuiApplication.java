package com.example.tweeterwebui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@SpringBootApplication
@EnableOAuth2Sso
public class TweeterWebuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweeterWebuiApplication.class, args);
	}

	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext clientContext, OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, clientContext);
	}
}

