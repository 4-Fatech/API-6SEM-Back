package com.fatech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getByEmail(String email);

    Optional<Usuario> findByEmail(String email);

   

    @Query("SELECT u FROM Usuario u WHERE u.tipo_usuario = :tipoUsuario")
    List<Usuario> findGuards(@Param("tipoUsuario") String tipoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.tipo_usuario = 'ROLE_ADMIN'")
    List<Usuario> findAdmins(@Param("tipoUsuario") String tipoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.tipo_usuario = 'ROLE_MANAGER'")
    List<Usuario> findManagers(@Param("tipoUsuario") String tipoUsuario);

    
    @Query("SELECT u.nome_usuario, COUNT(d) AS numDepartamentos FROM Usuario u " +
           "JOIN Departamento d ON u.id_usuario = d.responsavel_id.id_usuario " +
           "GROUP BY u.id_usuario, u.nome_usuario")
    List<Object[]> countDepartamentosByUsuario();

    @Query("SELECT u FROM Usuario u WHERE u.id_usuario = " +
           "(SELECT d.responsavel_id.id_usuario FROM Departamento d " +
           "GROUP BY d.responsavel_id.id_usuario " +
           "ORDER BY COUNT(d.id_departamento) DESC LIMIT 1)")
    Usuario findUsuarioWithMostDepartamentos();


    
    @Query("SELECT u, COUNT(r) FROM Redzone r JOIN r.responsavel_id u GROUP BY u ORDER BY COUNT(r) DESC")
    List<Object[]> findUsuarioWithMostRedzones();

     
     @Query("SELECT COUNT(u) FROM Usuario u")
     Long findTotalUsuarioCount();
 
     
     @Query("SELECT u.tipo_usuario, COUNT(u) FROM Usuario u GROUP BY u.tipo_usuario")
     List<Object[]> findUsuarioCountByTipoUsuario();
 
}
