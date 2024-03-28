package com.fatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatech.dto.LogDTO;
import com.fatech.entity.Log;
import com.fatech.repository.LogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service 
public class LogService {
    @Autowired
    private LogRepository logRepo;

    public List<LogDTO> buscarTodosLogs() {
        List<Log> logs = logRepo.findAll();
        return logs.stream()
                   .map(log -> new LogDTO(log.getId(), log.getEntradaAsString(), log.getData()))
                   .collect(Collectors.toList());
    }

    public Log criarLog(Log log) {
        return logRepo.save(log);
    }

    public LogDTO buscarLogPorId(Long id) {
        Optional<Log> optionalLog = logRepo.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            return new LogDTO(log.getId(), log.getEntradaAsString(), log.getData());
        } else {
            throw new RuntimeException("Log não encontrado para o ID: " + id);
        }
    }
}