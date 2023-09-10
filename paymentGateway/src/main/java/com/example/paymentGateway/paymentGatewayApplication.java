package com.example.paymentGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class paymentGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(paymentGatewayApplication.class, args);
		System.out.println("The Payment Gateway is running.............");
	}

}
