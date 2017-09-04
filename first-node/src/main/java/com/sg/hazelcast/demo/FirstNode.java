package com.sg.hazelcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;

@SpringBootApplication(exclude = HazelcastAutoConfiguration.class)
public class FirstNode {

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SpringApplication.run(FirstNode.class, args);
	}
}
