package com.fatech.repository;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fatech.entity.Departamento;


public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.responsavel_id.id_usuario = :idUsuario")
    List<Departamento> findDepartamentosByResponsavelId(Long idUsuario);
   

    @Query("SELECT d, COUNT(r) AS redzone_count FROM Departamento d " +
           "LEFT JOIN Redzone r ON d.id_departamento = r.id_departamento.id_departamento " +
           "GROUP BY d.id_departamento " +
           "ORDER BY redzone_count DESC")
    List<Object[]> findDepartamentosWithRedzoneCountOrdered();

    @Query("SELECT COUNT(d) FROM Departamento d")
    long countTotalDepartamentos();

    @Query("SELECT d.nome_departamento, COUNT(r) FROM Departamento d LEFT JOIN Redzone r ON d.id_departamento = r.id_departamento.id_departamento GROUP BY d.nome_departamento")
    List<Object[]> findRedzoneCountByDepartamento();
    
}
