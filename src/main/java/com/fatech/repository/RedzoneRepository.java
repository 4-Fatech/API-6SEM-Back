package com.fatech.repository;



import java.util.List;
import java.util.List;
import java.util.Map;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.entity.Redzone;





public interface RedzoneRepository extends JpaRepository<Redzone, Long> {


   @Query("SELECT r FROM Redzone r WHERE r.id_departamento.id_departamento = :idDepartamento")
    List<Redzone> findByDepartamentoId(@Param("idDepartamento") Long idDepartamento);

    @Query("SELECT r FROM Redzone r WHERE r.responsavel_id.id_usuario = :idResponsavel")
    List<Redzone> findByResponsavelId(Long idResponsavel);

    @Query(value = "SELECT redzone.nome_redzone, log.* FROM redzone JOIN log ON redzone.id_redzone = log.id_redzone WHERE redzone.id_departamento = :id_departamento", nativeQuery = true)
    public List<Map<String, Object>> buscarRedzoneByIdDepartamento(@Param("id_departamento") Long idDepartamento);

    @Query(value= "SELECT * from redzone where id_departamento = :id_departamento", nativeQuery = true)
    public List<Redzone> buscarIdRedzoneByDepartamento(@Param("id_departamento") Long idDepartamento);

    
    @Query("SELECT u.nome_usuario, COUNT(r) FROM Redzone r JOIN r.responsavel_id u GROUP BY u.nome_usuario")
    List<Object[]> findRedzoneCountByUsuario();

    
    @Query("SELECT COUNT(r) FROM Redzone r")
    Long findTotalRedzoneCount();
    

}
