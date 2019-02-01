package com.example.tweeterwebui;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Controller
public class TweeterController {
	private final RestTemplate restTemplate;
	private final URI tweeterApiUri;

	public TweeterController(RestTemplate restTemplate, @Value("${tweeter.api.uri}") URI tweeterApiUri) {
		this.restTemplate = restTemplate;
		this.tweeterApiUri = tweeterApiUri;
	}
	
	@GetMapping("/")
	public String timelines(Model model) {
		URI timelineUri = UriComponentsBuilder
				.fromUri(tweeterApiUri)
				.pathSegment("timelines")
				.build()
				.toUri();
		List<Tweet> tweets = restTemplate.exchange(
							RequestEntity.get(timelineUri).build(), 
							new ParameterizedTypeReference<List<Tweet>>() {}
						).getBody();

		model.addAttribute("tweets", tweets);
		return "tweets";
	}
	
	@GetMapping("/tweets")
	public String tweets(Model model) {
		URI tweetsUri = UriComponentsBuilder
				.fromUri(tweeterApiUri)
				.pathSegment("tweets")
				.build()
				.toUri();
		List<Tweet> tweets = restTemplate.exchange(
							RequestEntity.get(tweetsUri).build(), 
							new ParameterizedTypeReference<List<Tweet>>() {}
						).getBody();

		model.addAttribute("tweets", tweets);
		return "tweets";
	}
	
	@PostMapping("/tweets")
	public String tweets(@RequestParam String text) {
		URI tweetsUri = UriComponentsBuilder
				.fromUri(tweeterApiUri)
				.pathSegment("tweets")
				.build()
				.toUri();
		Map<String, String> body = Collections.singletonMap("text", text);

		restTemplate.exchange(RequestEntity.post(tweetsUri).body(body), Void.class);
		return "redirect:/";	
	}

	@Getter
	@Setter
	public static final class Tweet {
		private String tweetId;
		private String username;
		private String text;
		private Date createdAt;
	}
}
