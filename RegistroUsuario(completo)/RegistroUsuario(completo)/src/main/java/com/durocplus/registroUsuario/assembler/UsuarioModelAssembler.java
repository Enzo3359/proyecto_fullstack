package com.durocplus.registroUsuario.assembler;


import com.durocplus.registroUsuario.controller.AuthController;
import com.durocplus.registroUsuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {

        var links = new ArrayList<Link>();


        links.add(linkTo(methodOn(AuthController.class).obtenerUsuario(usuario.getId())).withSelfRel());
        links.add(linkTo(methodOn(AuthController.class).listarUsuarios()).withRel("Usuarios"));


        String urlSuscripcionExterna = "http://localhost:8085/api/suscripciones/usuario/" + usuario.getId();
        links.add(Link.of(urlSuscripcionExterna).withRel("ver-suscripciones"));


        return EntityModel.of(usuario, links);
    }

}