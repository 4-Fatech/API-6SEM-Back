package com.fatech.service;

import com.fatech.entity.Departamento;
import com.fatech.repository.DepartamentoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Departamento criarDepartamento(Departamento departamento) {
        if (departamento == null || 
            departamento.getNome_departamento() == null ||
            departamento.getNome_departamento().isBlank() ||
            departamento.getResponsavel_id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos para criar o departamento");
        }
        return departamentoRepository.save(departamento);
    }
    public Departamento atualizarDepartamento(Departamento departamento) {
        if (departamento == null || 
            departamento.getId_departamento() <= 0 ||
            departamento.getNome_departamento() == null ||
            departamento.getNome_departamento().isBlank() ||
            departamento.getResponsavel_id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos para atualizar o departamento");
        }
        return departamentoRepository.save(departamento);
    }
    public List<Departamento> buscarTodosDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        if (departamentos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum departamento encontrado");
        }
        return departamentos;
    }
    
    public Departamento buscarDepartamentoPorId(long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de departamento inválido");
        }
        Optional<Departamento> departamentoOptional = departamentoRepository.findById(id);
        if (departamentoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado para o ID fornecido");
        }
        return departamentoOptional.get();
    }
    

    
    
}
