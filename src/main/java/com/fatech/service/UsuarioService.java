package com.fatech.service;

import com.fatech.entity.Usuario;
import com.fatech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null ||
                usuario.getEmail() == null ||
                usuario.getEmail().isBlank() ||
                usuario.getMatricula_empresa() == null ||
                usuario.getMatricula_empresa().isBlank() ||
                usuario.getNome_usuario() == null ||
                usuario.getNome_usuario().isBlank() ||
                // usuario.getSenha() == null ||
                // usuario.getSenha().isBlank() ||
                usuario.getTipo_usuario() == null ||
                usuario.getTipo_usuario().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }

        return usuarioOp.get();

    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void desativarUsuario(long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.desativar(); // Desativa o usuário
            usuarioRepository.save(usuario); // Salva as alterações
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao desativar usuário.");
        }
    }

}
