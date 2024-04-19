package com.fatech.service;

import com.fatech.entity.Redzone;
import com.fatech.repository.RedzoneRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
public class RedzoneService {

    @Autowired
    private RedzoneRepository redzoneRepository;

    public Redzone criarRedzone(Redzone redzone) {

        if (redzone == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redzone não pode ser nulo");
        }
        if (redzone.getNome_redzone() == null || redzone.getNome_redzone().isEmpty() ||
            redzone.getCamera() == null || redzone.getCamera().isEmpty() ||
            redzone.getCapacidade_maxima() <= 0 ||
            redzone.getId_departamento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos os campos obrigatórios devem ser fornecidos");
        }
    
        return redzoneRepository.save(redzone);
    }
    public List<Redzone> buscarTodasRedzones() {
        List<Redzone> redzones = redzoneRepository.findAll();
        if (redzones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma redzone encontrada");
        }
        return redzones;
    }
    
    public Redzone buscarRedzonePorId(long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de redzone inválido");
        }
        Optional<Redzone> redzoneOptional = redzoneRepository.findById(id);
        if (redzoneOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Redzone não encontrada para o ID fornecido");
        }
        return redzoneOptional.get();
    }


   
    
}
