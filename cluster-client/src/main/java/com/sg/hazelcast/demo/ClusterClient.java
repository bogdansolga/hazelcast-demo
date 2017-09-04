package com.sg.hazelcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClusterClient {

	public static void main(String[] args) {
		SpringApplication.run(ClusterClient.class, args);
	}
}
