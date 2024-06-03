package com.fatech.repository;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fatech.entity.Departamento;


public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.responsavel_id.id_usuario = :idUsuario")
    List<Departamento> findDepartamentosByResponsavelId(Long idUsuario);
   

    @Query("SELECT d FROM Departamento d WHERE d.id_departamento = " +
           "(SELECT r.id_departamento.id_departamento FROM Redzone r " +
           "GROUP BY r.id_departamento.id_departamento " +
           "ORDER BY COUNT(r.id_redzone) DESC LIMIT 1)")
    Departamento findDepartamentoWithMostRedzones();

    @Query("SELECT COUNT(d) FROM Departamento d")
    long countTotalDepartamentos();

    @Query("SELECT d.nome_departamento, COUNT(r) FROM Departamento d LEFT JOIN Redzone r ON d.id_departamento = r.id_departamento.id_departamento GROUP BY d.nome_departamento")
    List<Object[]> findRedzoneCountByDepartamento();
    
}
