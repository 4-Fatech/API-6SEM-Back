package com.fatech.repository;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fatech.entity.Departamento;


public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.responsavel_id.id_usuario = :idUsuario")
    List<Departamento> findDepartamentosByResponsavelId(Long idUsuario);
   
    
}
