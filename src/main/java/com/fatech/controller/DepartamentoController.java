package com.fatech.controller;

import com.fatech.entity.Departamento;
import com.fatech.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/departamentos")
@CrossOrigin
@Tag(name = "Departamento")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;


    @Operation(summary = "Criar um departamento", description = "Cria um novo departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o departamento")
    })
    @PostMapping
    public ResponseEntity<Departamento> criarDepartamento(@RequestBody Departamento departamento) {
        Departamento novoDepartamento = departamentoService.criarDepartamento(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDepartamento);
    }


    
}
