package com.fatech.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatech.dto.LogDTO;
import com.fatech.entity.Log;
import com.fatech.service.LogService;

@RestController
@RequestMapping(value = "/log")
@CrossOrigin
public class LogController {
    @Autowired
    private LogService service;

    @GetMapping
    public List<LogDTO> buscarTodosLogs() {
        return service.buscarTodosLogs();
    }

    @PostMapping("/post")
    public ResponseEntity<Log> criarLog(@RequestBody Log novoLog) {
        try {
            novoLog.setData(LocalDateTime.now());
            Log logCriado = service.criarLog(novoLog);
            return new ResponseEntity<>(logCriado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogDTO> buscarLogPorId(@PathVariable Long id) {
        try {
            LogDTO log = service.buscarLogPorId(id);
            return new ResponseEntity<>(log, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}