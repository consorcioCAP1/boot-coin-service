package com.nttdata.bootcamp.bootcoinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class BootCoinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootCoinServiceApplication.class, args);
	}

}
