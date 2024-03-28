package com.fatech.service;

import org.springframework.stereotype.Service;

import com.fatech.entity.Log;
import com.fatech.repository.LogRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service 
public class LogService{
    @Autowired
    private LogRepository logRepo;

    public List<Log> buscarTodosLogs() {
        List<Log> logs = logRepo.findAll();
        return logs;
    }
}