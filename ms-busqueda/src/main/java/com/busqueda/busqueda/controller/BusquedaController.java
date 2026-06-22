package com.busqueda.busqueda.controller;

import com.busqueda.busqueda.assembler.HistorialBusquedaModelAssembler;
import com.busqueda.busqueda.dto.BusquedaRequestDTO;
import com.busqueda.busqueda.dto.BusquedaResponseDTO;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import com.busqueda.busqueda.service.BusquedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/busquedas")
@RequiredArgsConstructor
@Tag(
        name = "Búsquedas",
        description = "Operaciones para realizar búsquedas y administrar su historial"
)
public class BusquedaController {

    private final BusquedaService busquedaService;
    private final HistorialBusquedaModelAssembler assembler;

    @Operation(
            summary = "Listar historial",
            description = "Obtiene todas las búsquedas almacenadas en el historial"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historial obtenido correctamente"
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HistorialBusquedaResponseDTO>>>
    obtenerTodos() {

        List<EntityModel<HistorialBusquedaResponseDTO>> historial =
                busquedaService.obtenerTodos()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        CollectionModel<EntityModel<HistorialBusquedaResponseDTO>> respuesta =
                CollectionModel.of(
                        historial,
                        linkTo(
                                methodOn(BusquedaController.class)
                                        .obtenerTodos()
                        ).withSelfRel()
                );

        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Buscar historial por ID",
            description = "Obtiene una búsqueda específica mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Búsqueda no encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HistorialBusquedaResponseDTO>> obtenerPorId(
            @Parameter(
                    description = "Identificador de la búsqueda",
                    example = "1"
            )
            @PathVariable Long id) {

        return busquedaService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Realizar búsqueda",
            description = "Realiza una búsqueda de contenido y guarda el resultado en el historial"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Búsqueda realizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de búsqueda inválidos"
            )
    })
    @PostMapping
    public ResponseEntity<BusquedaResponseDTO> buscar(
            @Valid @RequestBody BusquedaRequestDTO dto) {

        return ResponseEntity.status(201)
                .body(busquedaService.buscar(dto));
    }

    @Operation(
            summary = "Eliminar búsqueda",
            description = "Elimina una búsqueda almacenada en el historial"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Búsqueda eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Búsqueda no encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador de la búsqueda que se eliminará",
                    example = "1"
            )
            @PathVariable Long id) {

        if (busquedaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        busquedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}