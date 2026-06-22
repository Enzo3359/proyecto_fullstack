package com.durocplus.suscripciones.controller;

import com.durocplus.suscripciones.assembler.SuscripcionModelAssembler;
import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import com.durocplus.suscripciones.service.SuscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
@Tag(
        name = "Suscripciones",
        description = "Operaciones para crear, consultar y cancelar suscripciones"
)
public class SuscripcionController {

    private final SuscripcionService suscripcionService;
    private final SuscripcionModelAssembler assembler;

    @Operation(
            summary = "Crear suscripción",
            description = "Crea una suscripción para un usuario existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Suscripción creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de suscripción inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping
    public ResponseEntity<EntityModel<SuscripcionResponseDTO>> crear(
            @Valid @RequestBody SuscripcionRequestDTO dto) {

        EntityModel<SuscripcionResponseDTO> modelo =
                assembler.toModel(suscripcionService.crear(dto));

        return ResponseEntity
                .created(modelo.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(modelo);
    }

    @Operation(
            summary = "Listar suscripciones",
            description = "Obtiene todas las suscripciones registradas"
    )
    @ApiResponse(responseCode = "200", description = "Suscripciones obtenidas correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SuscripcionResponseDTO>>> obtenerTodas() {

        List<EntityModel<SuscripcionResponseDTO>> suscripciones =
                suscripcionService.obtenerTodas()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        CollectionModel<EntityModel<SuscripcionResponseDTO>> respuesta =
                CollectionModel.of(
                        suscripciones,
                        linkTo(methodOn(SuscripcionController.class)
                                .obtenerTodas())
                                .withSelfRel()
                );

        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Buscar suscripción por ID",
            description = "Obtiene una suscripción mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Suscripción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SuscripcionResponseDTO>> obtenerPorId(
            @Parameter(description = "Identificador de la suscripción", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(suscripcionService.obtenerPorId(id))
        );
    }

    @Operation(
            summary = "Listar suscripciones de un usuario",
            description = "Obtiene todas las suscripciones pertenecientes a un usuario"
    )
    @ApiResponse(responseCode = "200", description = "Suscripciones del usuario obtenidas correctamente")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<SuscripcionResponseDTO>>> obtenerPorUsuario(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long usuarioId) {

        List<EntityModel<SuscripcionResponseDTO>> suscripciones =
                suscripcionService.obtenerPorUsuario(usuarioId)
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        CollectionModel<EntityModel<SuscripcionResponseDTO>> respuesta =
                CollectionModel.of(
                        suscripciones,
                        linkTo(methodOn(SuscripcionController.class)
                                .obtenerPorUsuario(usuarioId))
                                .withSelfRel()
                );

        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Cancelar suscripción",
            description = "Cambia el estado de una suscripción a inactiva"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suscripción cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Suscripción no encontrada")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntityModel<SuscripcionResponseDTO>> cancelar(
            @Parameter(description = "Identificador de la suscripción", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(suscripcionService.cancelar(id))
        );
    }
}
