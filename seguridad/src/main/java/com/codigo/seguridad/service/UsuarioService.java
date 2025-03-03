package com.codigo.seguridad.service;

import com.codigo.seguridad.aggregates.request.UpdateUserRequest;
import com.codigo.seguridad.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {
    UserDetailsService userDetailsService();

    String userUpdate(Long id, UpdateUserRequest updateUserRequest);

    Usuario userInfo(String username);
}
