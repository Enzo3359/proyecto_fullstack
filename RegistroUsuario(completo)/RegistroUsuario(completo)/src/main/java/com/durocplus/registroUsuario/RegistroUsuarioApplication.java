package com.durocplus.registroUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegistroUsuarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistroUsuarioApplication.class, args);
    }
}
