package com.codigo.seguridad.controller;


import com.codigo.seguridad.aggregates.request.UpdateUserRequest;
import com.codigo.seguridad.entity.Usuario;
import com.codigo.seguridad.service.AuthenticationService;
import com.codigo.seguridad.service.JwtService;
import com.codigo.seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @GetMapping("/user")
    public ResponseEntity<List<Usuario>> getUsers(){
        return ResponseEntity.ok(authenticationService.todoUsers());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Usuario>> getAdmins(){
        return ResponseEntity.ok(authenticationService.todoAdmins());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                                         @RequestBody UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(usuarioService.userUpdate(id, updateUserRequest));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<String> updateAdminr(@PathVariable Long id,
                                             @RequestBody UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(usuarioService.userUpdate(id, updateUserRequest));
    }

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");  // Remove "Bearer " prefix
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(username);
    }


}
