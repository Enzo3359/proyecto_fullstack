package com.busqueda.busqueda.assembler;

import com.busqueda.busqueda.controller.BusquedaController;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistorialBusquedaModelAssembler implements RepresentationModelAssembler<HistorialBusquedaResponseDTO, EntityModel<HistorialBusquedaResponseDTO>> {

    @Override
    public EntityModel<HistorialBusquedaResponseDTO> toModel(HistorialBusquedaResponseDTO historial) {
        ArrayList<Link> links = new ArrayList<Link>();

        // 1. Enlaces locales de este microservicio de búsqueda (Puerto 8086)
        links.add(linkTo(methodOn(BusquedaController.class).obtenerPorId(historial.getId())).withSelfRel());
        links.add(linkTo(methodOn(BusquedaController.class).obtenerTodos()).withRel("historial-completo"));

        // 2. Enlace externo al microservicio de catálogo (Puerto 8081)
        // Redirecciona dinámicamente al género de la película relacionada a la búsqueda
        if (historial.getGeneroId() != null) {
            String urlGeneroCatalogo = "http://localhost:8081/api/v1/generos/" + historial.getGeneroId();
            links.add(Link.of(urlGeneroCatalogo).withRel("ver-genero-catalogo"));
        }

        return EntityModel.of(historial, links);
    }
}