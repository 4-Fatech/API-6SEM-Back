package com.fatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.entity.Departamento;
import com.fatech.entity.Usuario;

public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {

     @Query("SELECT d FROM Departamento d WHERE d.responsavel_id = :responsavel")
    List<Departamento> findByResponsavel(@Param("responsavel") Usuario responsavel);

    
}
