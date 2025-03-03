package com.codigo.empresas_microservicios.controller;

import com.codigo.empresas_microservicios.client.SeguridadClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@RefreshScope
public class UserController {
    private final SeguridadClient seguridadClient;

    @GetMapping("/info-users")
    public ResponseEntity<String> getSaludo(
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(seguridadClient.getInfoSaludo(token));
    }


}
