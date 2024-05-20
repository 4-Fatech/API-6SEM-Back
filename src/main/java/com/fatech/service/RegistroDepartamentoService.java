package com.fatech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatech.entity.Redzone;
import com.fatech.repository.RedzoneRepository;


@Service
public class RegistroDepartamentoService {
    
    
    @Autowired
    private RedzoneRepository redzoneRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<Map<String, Object>> buscarRedzonePorDepartamento(Long id_departamento) {
        if (id_departamento <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do Departamento inválido");
        }

        List<Map<String, Object>> redzones = redzoneRepository.buscarRedzoneByIdDepartamento(id_departamento);

        if (redzones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi encontrado nenhuma redzone cadastrada neste departamento");
        }

        List<Map<String, Object>> redzonesModificados = redzones.stream()
                .map(redzone -> {
                    Map<String, Object> redzoneModificado = new HashMap<>(redzone);
                    boolean entrada = (boolean) redzoneModificado.get("entrada");
                    redzoneModificado.put("entrada", entrada ? "Saída" : "Entrada");
                    return redzoneModificado;
                })
                .collect(Collectors.toList());

        return redzonesModificados;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<Redzone> buscarIdRedzoneByDepartamento(Long id_departamento) {
        if (id_departamento <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do Departamento inválido");
        }
        List<Redzone> idRedzones = redzoneRepository.buscarIdRedzoneByDepartamento(id_departamento);
        if (idRedzones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi encontrado nenhuma redzone cadastrada neste departamento");
        }
        return idRedzones;
    }
}
