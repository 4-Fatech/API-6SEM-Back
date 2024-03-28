package com.fatech.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LogDTO {
    private Long id;
    private String entrada;
    
    

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String data;

    private int lotacaoAtual;

    public LogDTO(Long id, String entrada,  LocalDateTime data, int lotacaoAtual) {
        this.id = id;
        this.entrada = entrada;
        this.data = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lotacaoAtual = lotacaoAtual;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLotacaoAtual() {
        return lotacaoAtual;
    }

    public void setLotacaoAtual(int lotacaoAtual) {
        this.lotacaoAtual = lotacaoAtual;
    }

}