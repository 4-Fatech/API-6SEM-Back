package com.fatech.service;

import com.fatech.entity.Redzone;
import com.fatech.repository.RedzoneRepository;
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

   
    
}
