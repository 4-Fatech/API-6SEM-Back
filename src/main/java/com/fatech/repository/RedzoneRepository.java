package com.fatech.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.entity.Redzone;





public interface RedzoneRepository extends JpaRepository<Redzone, Long> {

   @Query("SELECT r FROM Redzone r WHERE r.id_departamento.id_departamento = :idDepartamento")
    List<Redzone> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);

    @Query("SELECT r FROM Redzone r WHERE r.responsavel_id.id_usuario = :idResponsavel")
    List<Redzone> findByResponsavelId(Long idResponsavel);
}
