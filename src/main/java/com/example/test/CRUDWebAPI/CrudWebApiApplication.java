package com.example.test.CRUDWebAPI;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CrudWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudWebApiApplication.class, args);
	}
	@Bean
	public ModelMapper  modelMapper(){
		return new ModelMapper();
	}

}
