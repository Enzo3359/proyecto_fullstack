package ms_reproduccion.client;

import ms_reproduccion.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-registroUsuario", url = "${ms.auth.url}")
public interface AuthClient {

    @GetMapping("/api/v1/auth/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuario(@PathVariable Long id);
}