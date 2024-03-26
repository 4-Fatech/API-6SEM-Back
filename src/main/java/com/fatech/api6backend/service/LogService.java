package com.fatech.api6backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatech.api6backend.entity.Log;
import com.fatech.api6backend.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepo;

    public Log buscarPorId(Long id) {
        Optional<Log> logOp = logRepo.findById(id);
        if (logOp.isPresent()) {
            return logOp.get();
        }
        throw new IllegalArgumentException("Id inválido!");
    }

    public Log novoLog(Log log) {
        if (log == null || log.getEntrada() == null) {
            throw new IllegalArgumentException("Dados inválidos!");
        }
        
        // Definir a data atual antes de salvar
        log.setData(LocalDateTime.now()); // Ajuste para o novo nome do campo
        
        return logRepo.save(log);
    }
    
    public List<Log> buscarTodos() {
        return logRepo.findAll();
    }
}