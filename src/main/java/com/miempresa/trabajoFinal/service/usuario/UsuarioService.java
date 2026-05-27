package com.miempresa.trabajoFinal.service.usuario;

import com.miempresa.trabajoFinal.models.Usuario;

public interface UsuarioService {
    Usuario registrar(Usuario usuario);
    Usuario buscarPorUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
