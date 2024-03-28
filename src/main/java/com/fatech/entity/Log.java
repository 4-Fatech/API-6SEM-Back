package com.fatech.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entrada")
    private Boolean entrada;

    @Column(name = "data")
    private LocalDateTime data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEntrada(){
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;

    }

    public LocalDateTime getData(){
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;

    }


}
