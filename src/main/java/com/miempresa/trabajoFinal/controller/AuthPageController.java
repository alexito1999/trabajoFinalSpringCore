package com.miempresa.trabajoFinal.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miempresa.trabajoFinal.models.Usuario;
import com.miempresa.trabajoFinal.service.UsuarioService;

@Controller
public class AuthPageController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthPageController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (usuarioService.existsByUsername(username)) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "registro";
        }
        if (usuarioService.existsByEmail(email)) {
            model.addAttribute("error", "El email ya est\u00E1 registrado");
            return "registro";
        }

        Usuario usuario = new Usuario(username, email, passwordEncoder.encode(password), Usuario.Rol.ROLE_USER);
        usuarioService.registrar(usuario);

        return "redirect:/login?registrado";
    }
}
