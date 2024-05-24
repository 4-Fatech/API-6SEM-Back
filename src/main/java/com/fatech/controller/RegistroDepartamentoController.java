package com.fatech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fatech.entity.Redzone;
import com.fatech.entity.Views;
import com.fatech.service.RegistroDepartamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/registroDepartamento")
@CrossOrigin
@Tag(name = "RegistroDepartamento")
public class RegistroDepartamentoController {

    @Autowired
    private RegistroDepartamentoService registroDepartamentoService;

    @Operation(summary = "Busca os logs de todas as redzones do departamento", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/{id_departamento}")
    public ResponseEntity<List<Map<String, Object>>> buscarRedzoneByIdDepartamento(@PathVariable Long id_departamento) {
        List<Map<String, Object>> redzones = registroDepartamentoService.buscarRedzonePorDepartamento(id_departamento);
        if (redzones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(redzones);
    }

    @Operation(summary = "Busca os id da redzone de um departamento", method = "GET", description = "Busca registro por id_redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os registros"),
            @ApiResponse(responseCode = "400", description = "Não encontrado")
    })
    @GetMapping("/idredzone/{id_departamento}")
    @JsonView(Views.Redzone.class)
    public ResponseEntity<List<Redzone>> buscarIdRedzone(@PathVariable Long id_departamento) {
        List<Redzone> idRedzones = registroDepartamentoService.buscarIdRedzoneByDepartamento(id_departamento);
        if (idRedzones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(idRedzones);
    }

}
