package com.master.register_service.security.services;

import com.master.register_service.persistence.dto.UserDtoRegister;
import com.master.register_service.persistence.model.User;
import com.master.register_service.persistence.repository.UserRepositoryI;
import com.master.register_service.security.model.AuthResponse;
import com.master.register_service.security.model.LoginRequest;
import com.master.register_service.security.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de autenticación.
 */
@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepo;

    private final JWTServiceI jwtMngm;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthServiceImpl(UserRepositoryI userRepo, JWTServiceI jwtMngm, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtMngm = jwtMngm;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request La solicitud de registro.
     * @return La respuesta de autenticación.
     */
    @Override
    public UserDtoRegister register(RegisterRequest request) {
        User user = new User(request.getNombre(),
                request.getApellidos(), request.getCorreo(),
                passwordEncoder.encode(request.getContrasena()));

        userRepo.save(user);


        return convertToDtoRegister(user);
    }

    /**
     * Inicia sesión en el sistema.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta de autenticación.
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getCorreo(),
                request.getContrasena()));
        User user = userRepo.findByCorreo(request.getCorreo());

        var jwtToken = jwtMngm.getToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }



    /**
     * Convierte un objeto User en un objeto UserDto.
     *
     * @param user Objeto User a convertir.
     * @return Objeto UserDto convertido.
     */
    private UserDtoRegister convertToDtoRegister(User user) {
        UserDtoRegister userDto = new UserDtoRegister();
        userDto.setNombre(user.getNombre());
        userDto.setApellidos(user.getApellidos());
        userDto.setCorreo(user.getCorreo());
        return userDto;
    }

}
