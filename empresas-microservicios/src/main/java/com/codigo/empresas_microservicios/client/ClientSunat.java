package com.codigo.empresas_microservicios.client;

import com.codigo.empresas_microservicios.aggregates.response.SunatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sunat-reniec", url = "https://api.apis.net.pe/v2/sunat/ruc/")

public interface ClientSunat {
    @GetMapping(value = "/full", produces = "application/json" )
    SunatResponse getEmpresa(@RequestParam("numero") String numero,
                             @RequestHeader("Authorization") String authorization);
}
