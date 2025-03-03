package com.codigo.seguridad.service.impl;

import com.codigo.seguridad.aggregates.request.UpdateUserRequest;
import com.codigo.seguridad.entity.Rol;
import com.codigo.seguridad.entity.Role;
import com.codigo.seguridad.entity.Usuario;
import com.codigo.seguridad.repository.RolRepository;
import com.codigo.seguridad.repository.UsuarioRepository;
import com.codigo.seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en Base de datos"));
            }
        };
    }

    @Override
    public String userUpdate(Long id, UpdateUserRequest updateUserRequest) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setUsername(updateUserRequest.getUsername());
        usuario.setPassword(new BCryptPasswordEncoder().encode(updateUserRequest.getPassword()));
        usuario.setRole(updateUserRequest.getRole());
        Rol newRole = getRoles(Role.valueOf(updateUserRequest.getRole()));
        usuario.setRoles(new HashSet<>(Collections.singletonList(newRole)));

        usuarioRepository.save(usuario);

        return "Usuario actualizado";
    }

    @Override
    public Usuario userInfo(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).get();
        return usuario;
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("Error el rol no exixte: " + rolBuscado.name()));
    }


}
