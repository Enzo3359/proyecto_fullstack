package com.durocplus.registroUsuario.client;

import com.durocplus.registroUsuario.dto.SuscripcionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-suscripcion", url = "${ms.suscripcion.url}")
public interface SuscripcionClient {

    @GetMapping("/api/suscripciones/usuario/{usuarioId}")
    SuscripcionResponseDTO obtenerSuscripcionPorUsuario(@PathVariable Long usuarioId);
}