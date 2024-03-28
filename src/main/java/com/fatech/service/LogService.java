package com.fatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatech.dto.LogDTO;
import com.fatech.entity.Log;
import com.fatech.repository.LogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service 
public class LogService {
    @Autowired
    private LogRepository logRepo;

    public List<LogDTO> buscarTodosLogs() {
        List<Log> logs = logRepo.findAll();
        List<LogDTO> logDTOs = new ArrayList<>();
        int lotacaoAtual = 0;
    
        for (Log log : logs) {
            if (log.getEntrada() != null && log.getEntrada()) {
                lotacaoAtual++;
            } else if (log.getEntrada() != null && !log.getEntrada()) {
                lotacaoAtual--;
            }
            
            LogDTO logDTO = new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), lotacaoAtual);
            logDTOs.add(logDTO);
        }
        
        return logDTOs;
    }
    
    public Log criarLog(Log log) {
        return logRepo.save(log);
    }

    public LogDTO buscarLogPorId(Long id) {
        Optional<Log> optionalLog = logRepo.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            int lotacaoAtual = (log.getEntrada() != null && log.getEntrada()) ? 1 : 0;
            return new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), lotacaoAtual);
        } else {
            throw new RuntimeException("Log não encontrado para o ID: " + id);
        }
    }
}
