package com.fatech.service;

import com.fatech.entity.Departamento;
import com.fatech.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Departamento criarDepartamento(Departamento departamento) {
        if(departamento == null || 
        departamento.getNome_departamento() == null ||
        departamento.getNome_departamento().isBlank()

        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados Invalidos");
        }
        return departamentoRepository.save(departamento);
    }

    
    
}