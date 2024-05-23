package com.fatech.service;

import com.fatech.entity.Usuario;
import com.fatech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<Usuario> findGuards() {
        return usuarioRepository.findGuards("ROLE_GUARD");
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Usuario> findAdmins() {
        return usuarioRepository.findAdmins("ROLE_ADMIN");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Usuario> findManagers() {
        return usuarioRepository.findManagers("ROLE_MANAGER");
    }
   
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null ||
                usuario.getEmail() == null ||
                usuario.getEmail().isBlank() ||
                usuario.getMatricula_empresa() == null ||
                usuario.getMatricula_empresa().isBlank() ||
                usuario.getNome_usuario() == null ||
                usuario.getNome_usuario().isBlank() ||
                usuario.getSenha() == null ||
                usuario.getSenha().isBlank() ||
                usuario.getTipo_usuario() == null ||
                usuario.getTipo_usuario().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
        }
        return usuarioRepository.save(usuario);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> todosUsuario = usuarioRepository.findAll();
        if (todosUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado");
        }
        List<Usuario> usuariosOrdenados = todosUsuario.stream()
        .sorted(Comparator.comparing(Usuario::getId_usuario))
        .collect(Collectors.toList());
        return usuariosOrdenados;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }

        return usuarioOp.get();

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_GUARD')")
    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistenteOp = usuarioRepository.findById(usuario.getId_usuario());
        if (usuarioExistenteOp.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOp.get();

            
            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                usuario.setSenha(usuarioExistente.getSenha());
            }

            if (usuario.getTipo_usuario() == null || usuario.getTipo_usuario().isBlank()) {
                usuario.setTipo_usuario(usuarioExistente.getTipo_usuario());
            }

            usuarioExistente.setNome_usuario(usuario.getNome_usuario());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setMatricula_empresa(usuario.getMatricula_empresa());
            usuarioExistente.setTipo_usuario(usuario.getTipo_usuario());
            usuarioExistente.setUpdate_at(LocalDateTime.now());

            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void desativarUsuario(long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.desativar(); 
            usuarioRepository.save(usuario); 
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao desativar usuário.");
        }
    }

}
