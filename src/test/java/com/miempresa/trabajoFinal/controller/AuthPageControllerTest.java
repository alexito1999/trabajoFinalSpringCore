package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.models.Usuario;
import com.miempresa.trabajoFinal.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthPageControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthPageController authPageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authPageController).build();
    }

    @Test
    void login_returnsLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void registroForm_returnsRegistroView() throws Exception {
        mockMvc.perform(get("/registro"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"));
    }

    @Test
    void registro_newUser_redirectsToLogin() throws Exception {
        when(usuarioService.existsByUsername(anyString())).thenReturn(false);
        when(usuarioService.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doNothing().when(usuarioService).registrar(any(Usuario.class));

        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registrado"));

        verify(usuarioService, times(1)).registrar(any(Usuario.class));
    }

    @Test
    void registro_existingUsername_returnsRegistroViewWithError() throws Exception {
        when(usuarioService.existsByUsername(anyString())).thenReturn(true);

        mockMvc.perform(post("/registro")
                        .param("username", "existinguser")
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "El nombre de usuario ya existe"));

        verify(usuarioService, never()).registrar(any(Usuario.class));
    }

    @Test
    void registro_existingEmail_returnsRegistroViewWithError() throws Exception {
        when(usuarioService.existsByUsername(anyString())).thenReturn(false);
        when(usuarioService.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(post("/registro")
                        .param("username", "testuser")
                        .param("email", "existing@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "El email ya está registrado"));

        verify(usuarioService, never()).registrar(any(Usuario.class));
    }
}
