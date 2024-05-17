package com.fatech.controller;

import com.fatech.entity.Usuario;
import com.fatech.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tipos")
@CrossOrigin
public class TiposUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Buscar todos os guardas", description = "Retorna todos os guardas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os guardas"),
            @ApiResponse(responseCode = "400", description = "Não há guardas cadastrados")
    })
    @GetMapping("/guardas")
    public List<Usuario> getGuards() {
        return usuarioService.findGuards();
    }

    @Operation(summary = "Buscar todos os gerentes geral", description = "Retorna todos os gerentes geral")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os gerentes geral"),
            @ApiResponse(responseCode = "400", description = "Não há gerentes geral cadastrados")
    })
    @GetMapping("/gerentes")
    public List<Usuario> getAdmins() {
        return usuarioService.findAdmins();
    }

    @Operation(summary = "Buscar todos os gerentes de área", description = "Retorna todos os gerentes de área")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os gerentes de área"),
            @ApiResponse(responseCode = "400", description = "Não há gerentes de área cadastrados")
    })
    @GetMapping("/area")
    public List<Usuario> getManagers() {
        return usuarioService.findManagers();
    }
}
