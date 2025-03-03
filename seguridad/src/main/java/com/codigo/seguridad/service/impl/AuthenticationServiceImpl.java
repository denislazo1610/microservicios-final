package com.codigo.seguridad.service.impl;

import com.codigo.seguridad.aggregates.constants.Constants;
import com.codigo.seguridad.aggregates.request.SignInRequest;
import com.codigo.seguridad.aggregates.request.SignUpRequest;
import com.codigo.seguridad.aggregates.response.SignInResponse;
import com.codigo.seguridad.entity.Rol;
import com.codigo.seguridad.entity.Role;
import com.codigo.seguridad.entity.Usuario;
import com.codigo.seguridad.repository.RolRepository;
import com.codigo.seguridad.repository.UsuarioRepository;
import com.codigo.seguridad.service.AuthenticationService;
import com.codigo.seguridad.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Usuario signUpUsuario(SignUpRequest signUpRequest) {
        Usuario usuario = getUsuarioEntity(signUpRequest);
        if(signUpRequest.getRole().equals("USER") ){
            usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        } else if (signUpRequest.getRole().equals("ADMIN")) {
            usuario.setRoles(Collections.singleton(getRoles(Role.ADMIN)));
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> todoAdmins() {
        return usuarioRepository.findByRole("ADMIN");
    }

    @Override
    public List<Usuario> todoUsers() {
        return usuarioRepository.findByRole("USER");
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername()).orElseThrow(
                ()-> new UsernameNotFoundException("Error usuario no encontrado!!"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        return SignInResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    //GENERAR UN ACCESS TOKEN A PARTIR DE UN REFRESHTOKEN
    @Override
    public SignInResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException {
        //VALIDAR QUE SEA UN REFRESHTOKEN
        log.info("Ejecutando - getTokenByRefreshToken");
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new RuntimeException("Error el token ingresado no es un REFRESH ");
        }
        //EXTRAER EL USUARIO
        String userEmail = jwtService.extractUsername(refreshToken);

        //BUSCAMOS AL USUARIO EN BD
        Usuario usuario = usuarioRepository.findByUsername(userEmail).orElseThrow(
                ()-> new UsernameNotFoundException("Error usuario no encontrado"));
        //VALIDAR QUE EL REFRESHTOKEN LE PERTENEZCA A UN USUARIO
        if(!jwtService.validateToken(refreshToken, usuario)){
            throw new IllegalAccessException("Error el token no le pertenece a al usuario");
        }
        //GENERAR EL ACCESSTOKEN
        String newToken = jwtService.generateToken(usuario);
        return SignInResponse.builder()
                .token(newToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Usuario getUsuarioEntity(SignUpRequest signUpRequest){
        return Usuario.builder()
                .username(signUpRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()))
                .isAccountNonExpired(Constants.STATUS_ACTIVE)
                .isAccountNonLocked(Constants.STATUS_ACTIVE)
                .isCredentialsNonExpired(Constants.STATUS_ACTIVE)
                .isEnabled(Constants.STATUS_ACTIVE)
                .role(signUpRequest.getRole())
                .build();
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("Error el rol no exixte: " + rolBuscado.name()));
    }
}
