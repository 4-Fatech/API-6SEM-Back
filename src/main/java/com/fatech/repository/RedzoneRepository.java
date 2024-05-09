package com.fatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fatech.entity.Redzone;
import com.fatech.entity.Usuario;

import jakarta.transaction.Transactional;

public interface RedzoneRepository extends JpaRepository<Redzone, Long> {

    @Query("SELECT r FROM Redzone r WHERE r.id_departamento.id_departamento = :idDepartamento")
    List<Redzone> listarRedzonesPorDepartamento(Long idDepartamento);

    @Modifying
    @Transactional
    @Query("UPDATE Redzone r SET r.responsavel_id = ?2 WHERE r.id_redzone = ?1")
    void atualizarResponsavelRedzone(Long idRedzone, Usuario novoResponsavel);

}
