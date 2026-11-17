package com.tom.chess_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class ChessServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessServerApplication.class, args);
	}

}
