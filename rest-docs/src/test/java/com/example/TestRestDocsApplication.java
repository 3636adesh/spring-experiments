package com.example;

import com.example.common.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestRestDocsApplication {

	public static void main(String[] args) {
		SpringApplication.from(RestDocsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
