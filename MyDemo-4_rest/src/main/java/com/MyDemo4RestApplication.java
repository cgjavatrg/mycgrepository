package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;




@SpringBootApplication

public class MyDemo4RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDemo4RestApplication.class, args);
	}

}


// swagger url ==> http://localhost:8082/swagger-ui/index.html