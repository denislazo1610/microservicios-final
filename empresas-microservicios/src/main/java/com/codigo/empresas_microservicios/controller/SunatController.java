package com.codigo.empresas_microservicios.controller;

import com.codigo.empresas_microservicios.aggregates.request.SunatRegistrarse;
import com.codigo.empresas_microservicios.aggregates.response.SunatResponse;
import com.codigo.empresas_microservicios.client.SeguridadClient;
import com.codigo.empresas_microservicios.entity.EmpresaEntity;
import com.codigo.empresas_microservicios.service.SunatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consulta")
@RefreshScope
public class SunatController {
    @Autowired
    private SunatService sunatService;

    @Value("${dato.property}")
    private String datoProp;

    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        return new ResponseEntity<>(datoProp , HttpStatus.OK);
    }

    @GetMapping("/buscarPorRuc/{ruc}")
    public ResponseEntity<Object> buscarPorRUC(
            @PathVariable String ruc){
        return new ResponseEntity<>(sunatService.buscarPorRuc(ruc), HttpStatus.OK);
    }

    @PostMapping("/empresas")
    public ResponseEntity<String> registrarEmpresa(
            @RequestBody SunatRegistrarse sunatRegistrarse,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader){
        if (!isAuthorized(authHeader)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(sunatService.resgitrarEmpresa(sunatRegistrarse.getRuc(), authHeader), HttpStatus.OK);
    }

    @GetMapping("/empresas/{ruc}")
    public ResponseEntity<Object> buscarEmpresa(
            @PathVariable String ruc,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader){
        if (!isAuthorized(authHeader)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(sunatService.buscarEmpresaEnBD(ruc), HttpStatus.OK);
    }

    private boolean isAuthorized(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

}
