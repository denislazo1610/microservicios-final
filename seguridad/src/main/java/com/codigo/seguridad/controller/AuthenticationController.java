package com.codigo.seguridad.controller;

import com.codigo.seguridad.aggregates.request.SignInRequest;
import com.codigo.seguridad.aggregates.request.SignUpRequest;
import com.codigo.seguridad.aggregates.request.ValidateRequest;
import com.codigo.seguridad.aggregates.response.SignInResponse;
import com.codigo.seguridad.entity.Usuario;
import com.codigo.seguridad.service.AuthenticationService;
import com.codigo.seguridad.service.JwtService;
import com.codigo.seguridad.service.UsuarioService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/auth/")
@RequiredArgsConstructor
@RefreshScope
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Value("${dato.propiedad}")
    private String datoProp;

    @PostMapping("/register")
    public ResponseEntity<Usuario> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpUsuario(signUpRequest));
    }

    @GetMapping("/prop")
    public ResponseEntity<String> getDatoProp(){
        return ResponseEntity.ok(datoProp);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Usuario>> getAdmins(){
        return ResponseEntity.ok(authenticationService.todoAdmins());
    }


    @GetMapping("/clave")
    public ResponseEntity<String> getClave(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String dato = Base64.getEncoder().encodeToString(key.getEncoded());
        return ResponseEntity.ok(dato);
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/validatetoken")
    public ResponseEntity<String> signIn(
            @RequestBody ValidateRequest validateRequest,
            @RequestParam String token
            ){
        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(validateRequest.getUsername());
        return ResponseEntity.ok(jwtService.validandoToken(userDetails, token));
    }

    @GetMapping("/user-info-test")
    public ResponseEntity<Usuario> infoUser(
            @RequestParam String token
    ){
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(usuarioService.userInfo(username));
    }



    @PostMapping("/refreshtoken")
    public ResponseEntity<SignInResponse> refreshToken(
            @RequestParam String refreshToken) throws IllegalAccessException {
        return ResponseEntity.ok(authenticationService.getTokenByRefreshToken(refreshToken));
    }
}
