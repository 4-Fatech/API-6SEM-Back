package com.fatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatech.entity.Log;
import com.fatech.service.LogService;


@RestController
@RequestMapping(value = "/log")
@CrossOrigin
public class LogController {
    @Autowired
    private LogService service;

    @GetMapping
    public List<Log> buscarTodosLogs() {
        return service.buscarTodosLogs();
    }
    
}
