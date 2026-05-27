package com.miempresa.trabajoFinal.controller;

import com.miempresa.trabajoFinal.models.Usuario;
import com.miempresa.trabajoFinal.security.JwtTokenProvider;
import com.miempresa.trabajoFinal.service.usuario.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void login_validCredentials_returnsJwtResponse() throws Exception {
        Usuario usuario = new Usuario("testuser", "test@test.com", "encoded", Usuario.Rol.ROLE_USER);
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(tokenProvider.generateToken(usuario)).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("fake-jwt-token"))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    void register_newUser_returnsCreated() throws Exception {
        when(usuarioService.existsByUsername("newuser")).thenReturn(false);
        when(usuarioService.existsByEmail("new@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(tokenProvider.generateToken(any())).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"email\":\"new@test.com\",\"password\":\"password123\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value("fake-jwt-token"))
            .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void register_existingUsername_returnsBadRequest() throws Exception {
        when(usuarioService.existsByUsername("existing")).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"existing\",\"email\":\"test@test.com\",\"password\":\"password123\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_existingEmail_returnsBadRequest() throws Exception {
        when(usuarioService.existsByUsername("newuser")).thenReturn(false);
        when(usuarioService.existsByEmail("existing@test.com")).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"email\":\"existing@test.com\",\"password\":\"password123\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void login_blankUsername_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"\",\"password\":\"password123\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_blankFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"\",\"email\":\"\",\"password\":\"\"}"))
            .andExpect(status().isBadRequest());
    }
}
