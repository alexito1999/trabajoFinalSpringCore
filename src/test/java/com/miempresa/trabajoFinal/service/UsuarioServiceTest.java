package com.miempresa.trabajoFinal.service;

import com.miempresa.trabajoFinal.models.Usuario;
import com.miempresa.trabajoFinal.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@Transactional
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void registrar_guardaUsuario() {
        Usuario u = new Usuario("testuser", "test@test.com", "encoded", Usuario.Rol.ROLE_USER);
        Usuario guardado = usuarioService.registrar(u);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getUsername()).isEqualTo("testuser");
        assertThat(guardado.getEmail()).isEqualTo("test@test.com");
        assertThat(guardado.getRol()).isEqualTo(Usuario.Rol.ROLE_USER);
    }

    @Test
    void existsByUsername_existente_retornaTrue() {
        usuarioService.registrar(new Usuario("existing", "e@t.com", "pwd", Usuario.Rol.ROLE_USER));

        assertThat(usuarioService.existsByUsername("existing")).isTrue();
        assertThat(usuarioService.existsByUsername("nonexistent")).isFalse();
    }

    @Test
    void existsByEmail_existente_retornaTrue() {
        usuarioService.registrar(new Usuario("user", "email@test.com", "pwd", Usuario.Rol.ROLE_USER));

        assertThat(usuarioService.existsByEmail("email@test.com")).isTrue();
        assertThat(usuarioService.existsByEmail("other@test.com")).isFalse();
    }

    @Test
    void buscarPorUsername_existente_retornaUsuario() {
        usuarioService.registrar(new Usuario("findme", "f@t.com", "pwd", Usuario.Rol.ROLE_USER));

        Usuario encontrado = usuarioService.buscarPorUsername("findme");
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getUsername()).isEqualTo("findme");
    }

    @Test
    void buscarPorUsername_inexistente_retornaNull() {
        assertThat(usuarioService.buscarPorUsername("nobody")).isNull();
    }

    @Test
    void registrar_variosUsuarios_verificaExistenciaIndependiente() {
        usuarioService.registrar(new Usuario("user1", "u1@t.com", "pwd1", Usuario.Rol.ROLE_USER));
        usuarioService.registrar(new Usuario("user2", "u2@t.com", "pwd2", Usuario.Rol.ROLE_ADMIN));

        assertThat(usuarioService.existsByUsername("user1")).isTrue();
        assertThat(usuarioService.existsByUsername("user2")).isTrue();
        assertThat(usuarioService.existsByEmail("u1@t.com")).isTrue();
        assertThat(usuarioService.existsByEmail("u2@t.com")).isTrue();
        assertThat(usuarioService.existsByEmail("u3@t.com")).isFalse();

        assertThat(usuarioRepository.count()).isEqualTo(2);
    }
}
