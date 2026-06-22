package com.durocplus.suscripciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-registroUsuario", url = "${ms.usuario.url}")
public interface UsuarioClient {

    @GetMapping("/api/v1/auth/usuarios/{id}")
    Object obtenerUsuario(@PathVariable Long id);
}