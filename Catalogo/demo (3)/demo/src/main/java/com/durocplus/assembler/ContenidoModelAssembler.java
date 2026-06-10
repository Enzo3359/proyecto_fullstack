package com.durocplus.assembler;

import com.durocplus.controller.ContenidoController;
import com.durocplus.dto.ContenidoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContenidoModelAssembler implements RepresentationModelAssembler<ContenidoResponseDTO, EntityModel<ContenidoResponseDTO>> {

    @Override
    public EntityModel<ContenidoResponseDTO> toModel(ContenidoResponseDTO contenido) {
        ArrayList<Link> links = new ArrayList<Link>();

        // 1. Enlaces locales del propio microservicio de catálogo (Puerto 8081)
        links.add(linkTo(methodOn(ContenidoController.class).obtenerPorId(contenido.getId())).withSelfRel());
        links.add(linkTo(methodOn(ContenidoController.class).listarTodos()).withRel("todos-los-contenidos"));

        // 2. Enlace externo al microservicio de búsquedas (Puerto 8086)
        // Permite simular la acción de buscar este contenido externamente
        String urlBusquedaExterna = "http://localhost:8086/api/busquedas";
        links.add(Link.of(urlBusquedaExterna).withRel("buscar-en-sistema"));

        return EntityModel.of(contenido, links);
    }
}
