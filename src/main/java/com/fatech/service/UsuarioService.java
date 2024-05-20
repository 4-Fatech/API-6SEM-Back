package com.fatech.service;

import com.fatech.entity.Usuario;
import com.fatech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.time.Duration;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    private Map<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();

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
   
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistenteOp = usuarioRepository.findById(usuario.getId_usuario());
        if (usuarioExistenteOp.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOp.get();

            
            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                usuario.setSenha(usuarioExistente.getSenha());
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
    public void enviarCodigoVerificacaoPorEmail(String email) {
    
    Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
    if (optionalUsuario.isPresent()) {
        String codigoVerificacao = gerarCodigoVerificacao();
        
        verificationCodes.put(email, new VerificationCode(codigoVerificacao, Instant.now()));
        try {
            emailService.enviarEmail(email, "template_a5flxiu", "64skWYeEq_nk8m4PE", "{\"email\":\"" + email + "\", \"codigoVerificacao\":\"" + codigoVerificacao + "\"}");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar email: " + e.getMessage());
        }
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
    }
}


private static class VerificationCode {
    private String code;
    private Instant createdAt;

    public VerificationCode(String code, Instant createdAt) {
        this.code = code;
        this.createdAt = createdAt;
    }

    public String getCode() {
        return code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}


public boolean verificarCodigoVerificacao(String email, String codigo) {
    VerificationCode verificationCode = verificationCodes.get(email);
    if (verificationCode != null && verificationCode.getCode().equals(codigo)) {
        
        return Duration.between(verificationCode.getCreatedAt(), Instant.now()).toMinutes() <= 5;
    }
    return false;
}
public void alterarSenha(String email, String codigo, String novaSenha) {
    
    if (verificarCodigoVerificacao(email, codigo)) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setSenha(novaSenha);
            usuarioRepository.save(usuario);
            
            verificationCodes.remove(email);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Código de verificação inválido ou expirado");
    }
}
    
    private String gerarCodigoVerificacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); 
        return String.valueOf(codigo);
    }

}


