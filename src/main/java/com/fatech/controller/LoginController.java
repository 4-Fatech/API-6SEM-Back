package com.fatech.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fatech.entity.Usuario;
import com.fatech.repository.UsuarioRepository;
import com.fatech.security.JwtUtils;
import com.fatech.security.Login;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody Login login) throws JsonProcessingException {
        Optional<Usuario> usuarioOp = usuarioRepository.getByEmail(login.getEmail());
        
        if (usuarioOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado!");
        }

        Usuario usuario = usuarioOp.get();

        if (usuario.getDelete_at() != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário inativo!");
        }

        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
            auth = authManager.authenticate(auth);
            login.setId(usuario.getId_usuario());
            login.setNomeUsuario(usuario.getNome_usuario());
            login.setToken(JwtUtils.generateToken(auth)); 
            login.setAutorizacoes(auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            login.setSenha(null);
            return ResponseEntity.ok(login);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Senha incorreta!");
        }
    }

}
