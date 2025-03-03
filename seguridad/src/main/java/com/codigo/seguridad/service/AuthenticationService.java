package com.codigo.seguridad.service;

import com.codigo.seguridad.aggregates.request.SignInRequest;
import com.codigo.seguridad.aggregates.request.SignUpRequest;
import com.codigo.seguridad.aggregates.response.SignInResponse;
import com.codigo.seguridad.entity.Usuario;

import java.util.List;

public interface AuthenticationService {
    //Registrar Usuario
    Usuario signUpUsuario(SignUpRequest signUpRequest);

    //
    List<Usuario> todoAdmins();
    List<Usuario> todoUsers();

    //Meotodo para login
    SignInResponse signIn(SignInRequest signInRequest);
    //Metodo para obtener un nuevo accesstoken a partir del refreshtoken
    SignInResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException;
}
