package com.codigo.empresas_microservicios.service;

import com.codigo.empresas_microservicios.aggregates.response.SunatResponse;
import com.codigo.empresas_microservicios.entity.EmpresaEntity;

public interface SunatService {
    SunatResponse buscarPorRuc(String ruc);
    EmpresaEntity buscarEmpresaEnBD(String ruc);
    String resgitrarEmpresa(String ruc, String authHeader) ;
}
