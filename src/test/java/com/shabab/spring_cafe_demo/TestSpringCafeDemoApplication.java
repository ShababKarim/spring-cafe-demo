package com.shabab.spring_cafe_demo;

import org.springframework.boot.SpringApplication;

public class TestSpringCafeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringCafeDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
