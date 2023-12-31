package com.checkmate.matchmaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MatchmakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchmakingApplication.class, args);
	}

}
