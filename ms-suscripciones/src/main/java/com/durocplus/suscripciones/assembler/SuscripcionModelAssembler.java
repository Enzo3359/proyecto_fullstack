package com.durocplus.suscripciones.assembler;

import com.durocplus.suscripciones.controller.SuscripcionController;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SuscripcionModelAssembler implements
        RepresentationModelAssembler<SuscripcionResponseDTO, EntityModel<SuscripcionResponseDTO>> {

    @Override
    @NonNull
    public EntityModel<SuscripcionResponseDTO> toModel(
            @NonNull SuscripcionResponseDTO suscripcion) {

        EntityModel<SuscripcionResponseDTO> modelo = EntityModel.of(
                suscripcion,
                linkTo(methodOn(SuscripcionController.class)
                        .obtenerPorId(suscripcion.getId()))
                        .withSelfRel(),
                linkTo(methodOn(SuscripcionController.class)
                        .obtenerTodas())
                        .withRel("suscripciones"),
                linkTo(methodOn(SuscripcionController.class)
                        .obtenerPorUsuario(suscripcion.getUsuarioId()))
                        .withRel("suscripciones-usuario")
        );

        if (Boolean.TRUE.equals(suscripcion.getActiva())) {
            modelo.add(
                    linkTo(methodOn(SuscripcionController.class)
                            .cancelar(suscripcion.getId()))
                            .withRel("cancelar")
            );
        }

        return modelo;
    }
}
