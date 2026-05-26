package com.miempresa.trabajoFinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miempresa.trabajoFinal.dto.JwtResponse;
import com.miempresa.trabajoFinal.dto.LoginRequest;
import com.miempresa.trabajoFinal.dto.RegisterRequest;
import com.miempresa.trabajoFinal.models.Usuario;
import com.miempresa.trabajoFinal.security.JwtTokenProvider;
import com.miempresa.trabajoFinal.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenProvider.generateToken(usuario);

        return ResponseEntity.ok(new JwtResponse(token, usuario.getUsername(), usuario.getRol().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
        }
        if (usuarioService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya est\u00E1 registrado");
        }

        Usuario usuario = new Usuario(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Usuario.Rol.ROLE_USER);

        usuarioService.registrar(usuario);

        String token = tokenProvider.generateToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtResponse(token, usuario.getUsername(), usuario.getRol().name()));
    }
}
