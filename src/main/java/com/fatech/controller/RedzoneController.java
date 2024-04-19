package com.fatech.controller;

import com.fatech.entity.Redzone;
import com.fatech.service.RedzoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/redzones")
@CrossOrigin
@Tag(name = "Redzone")
public class RedzoneController {

    @Autowired
    private RedzoneService redzoneService;

    @Operation(summary = "Criar uma redzone", description = "Cria uma nova redzone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Redzone criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a redzone")
    })
    @PostMapping
    public ResponseEntity<Redzone> criarRedzone(@RequestBody Redzone redzone) {
        Redzone novaRedzone = redzoneService.criarRedzone(redzone);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRedzone);
    }
    
}
