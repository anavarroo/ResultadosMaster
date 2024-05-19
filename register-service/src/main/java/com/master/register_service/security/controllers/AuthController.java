package com.master.register_service.security.controllers;

import com.master.register_service.persistence.dto.UserDtoRegister;
import com.master.register_service.security.model.AuthResponse;
import com.master.register_service.security.model.LoginRequest;
import com.master.register_service.security.model.RegisterRequest;
import com.master.register_service.security.services.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Order(1)

public class AuthController {

    private final AuthServiceImpl authMngm;

    @Autowired
    public AuthController(AuthServiceImpl authMngm) {
        this.authMngm = authMngm;
    }

    /**
     * Endpoint para el registro de usuarios.
     *
     * @param request Datos de registro del usuario.
     * @return Respuesta con el token de autenticación.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<UserDtoRegister> register(@RequestBody RegisterRequest request) {

        UserDtoRegister response = authMngm.register(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para la autenticación de usuarios.
     *
     * @param request Datos de inicio de sesión del usuario.
     * @return Respuesta con el token de autenticación.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authMngm.login(request);

        // Imprimir el token en la consola
        System.out.println("Token generado en el backend: " + authResponse.getToken());

        return ResponseEntity.ok(authResponse);
    }






}
