package com.fatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
