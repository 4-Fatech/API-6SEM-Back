package com.fatech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.fatech.dto.LogDTO;
import com.fatech.dto.LogSummary;
import com.fatech.entity.Log;
import com.fatech.entity.Redzone;
import com.fatech.repository.LogRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepo;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public Map<String, List<LogSummary>> getLogsByDepartmentAndDateRange(long departamentoId, LocalDate startDate, LocalDate endDate) {
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    List<LogSummary> logSummaries = logRepo.findLogsByDepartmentAndDateRange(departamentoId, startDateTime, endDateTime);

    Map<String, List<LogSummary>> result = new HashMap<>();

    for (LogSummary summary : logSummaries) {
        String date = summary.getDate();
        result.computeIfAbsent(date, k -> new ArrayList<>()).add(summary);
    }

    return result;
}

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<Object[]> countLogsByDateAndRedzone(Long redzoneId, LocalDate startDate, LocalDate endDate) {
        return logRepo.countLogsByDateAndRedzone(redzoneId, startDate, endDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_GUARD')")
    public List<Object[]> findLogCountByRedzoneIdAndDateRangeGroupedByDay(Redzone redzoneId, LocalDateTime startDate,
            LocalDateTime endDate) {
        return logRepo.findLogCountByRedzoneIdAndDateRangeGroupedByDay(redzoneId, startDate, endDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<Object[]> getRedzoneWithMostLogs() {
        return logRepo.findRedzoneWithMostLogs();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_GUARD')")
    public List<Log> findLogsByRedzoneId(Redzone redzoneId) {
        return logRepo.findByRedzoneId(redzoneId);
      }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_GUARD')")
    public List<LogDTO> buscarTodosLogs() {
        List<Log> logs = logRepo.findAll();
        return logs.stream()
                .map(log -> new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), log.getLotacao()))
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Log criarLog(Log log) {
        return logRepo.save(log);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_GUARD')")
    public LogDTO buscarLogPorId(Long id) {
        Optional<Log> optionalLog = logRepo.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            return new LogDTO(log.getId(), log.getEntradaAsString(), log.getData(), log.getLotacao());
        } else {
            throw new RuntimeException("Log não encontrado para o ID: " + id);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void deletarLog(Long id) {
        if (logRepo.existsById(id)) {
            logRepo.deleteById(id);
        } else {
            throw new RuntimeException("Log não encontrado para o ID: " + id);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void deletarTodosLogs() {
        logRepo.deleteAll();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_GUARD')")
    public List<Log> findByRedzoneIdAndDateRange(Redzone redzoneId, LocalDateTime startDate, LocalDateTime endDate) {
        return logRepo.findByRedzoneIdAndDateRange(redzoneId, startDate, endDate);
    }
    
}
