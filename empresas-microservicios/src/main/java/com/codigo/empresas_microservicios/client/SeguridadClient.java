package com.codigo.empresas_microservicios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "seguridad")
public interface SeguridadClient {
    @GetMapping("apis/codigo/api/users/getUsername")
    String getInfoSaludo(@RequestHeader("Authorization") String authorization);
}
