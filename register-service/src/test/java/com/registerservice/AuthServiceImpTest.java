package com.registerservice;

import com.master.register_service.persistence.dto.UserDtoRegister;
import com.master.register_service.persistence.model.User;
import com.master.register_service.persistence.repository.UserRepositoryI;
import com.master.register_service.security.model.AuthResponse;
import com.master.register_service.security.model.LoginRequest;
import com.master.register_service.security.model.RegisterRequest;
import com.master.register_service.security.services.AuthServiceImpl;
import com.master.register_service.security.services.JWTServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AuthServiceImpTest {

    @Mock
    private UserRepositoryI userRepo;

    @Mock
    private JWTServiceI jwtMngm;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Configuración: Crear un objeto RegisterRequest y un objeto User
        RegisterRequest registerRequest = new RegisterRequest("Joaquin", "Caños", "juaky.caños@example.com", "password123");
        User user = new User("Joaquin", "Caños", "juaky.caños@example.com", "encodedPassword");

        when(passwordEncoder.encode(registerRequest.getContrasena())).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        // Acción: Llamar al método register del servicio
        UserDtoRegister result = authService.register(registerRequest);

        // Afirmación: Verificar que los valores del resultado son correctos
        assertEquals("Joaquin", result.getNombre());
        assertEquals("Caños", result.getApellidos());
        assertEquals("juaky.caños@example.com", result.getCorreo());

        // Verificación: Verificar que el método save del repositorio fue llamado con un User con los valores correctos
        verify(userRepo).save(any(User.class));
    }

    @Test
    public void testLogin() {
        // Configuración: Crear un objeto LoginRequest y un objeto User
        LoginRequest loginRequest = new LoginRequest("juaky.caños@example.com", "password123");
        User user = new User("Joaquin", "Caños", "juaky.caños@example.com", "encodedPassword");

        when(userRepo.findByCorreo(loginRequest.getCorreo())).thenReturn(user);
        when(jwtMngm.getToken(user)).thenReturn("jwtToken");

        // Acción: Llamar al método login del servicio
        AuthResponse result = authService.login(loginRequest);

        // Afirmación: Verificar que el token en el resultado es el esperado
        assertEquals("jwtToken", result.getToken());

        // Verificación: Verificar que el método authenticate del AuthenticationManager fue llamado con los valores correctos
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
