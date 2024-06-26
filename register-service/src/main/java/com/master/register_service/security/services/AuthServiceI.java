package com.master.register_service.security.services;

import com.master.register_service.persistence.dto.UserDtoRegister;
import com.master.register_service.security.model.AuthResponse;
import com.master.register_service.security.model.LoginRequest;
import com.master.register_service.security.model.RegisterRequest;

/**
 * Interfaz para el servicio de autenticación.
 */
public interface AuthServiceI {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request La solicitud de registro.
     * @return La respuesta de autenticación.
     */
    UserDtoRegister register(RegisterRequest request);

    /**
     * Inicia sesión en el sistema.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta de autenticación.
     */
    AuthResponse login(LoginRequest request);

}
