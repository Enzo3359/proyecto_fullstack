package com.busqueda.busqueda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BusquedaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusquedaApplication.class, args);
	}

}