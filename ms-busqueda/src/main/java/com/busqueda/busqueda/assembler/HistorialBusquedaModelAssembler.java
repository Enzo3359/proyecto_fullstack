package com.busqueda.busqueda.assembler;

import com.busqueda.busqueda.controller.BusquedaController;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistorialBusquedaModelAssembler implements
        RepresentationModelAssembler<
                HistorialBusquedaResponseDTO,
                EntityModel<HistorialBusquedaResponseDTO>> {

    @Override
    @NonNull
    public EntityModel<HistorialBusquedaResponseDTO> toModel(
            @NonNull HistorialBusquedaResponseDTO historial) {

        return EntityModel.of(
                historial,

                linkTo(
                        methodOn(BusquedaController.class)
                                .obtenerPorId(historial.getId())
                ).withSelfRel(),

                linkTo(
                        methodOn(BusquedaController.class)
                                .obtenerTodos()
                ).withRel("historial")
        );
    }
}