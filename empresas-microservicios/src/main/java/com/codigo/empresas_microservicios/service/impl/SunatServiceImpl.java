package com.codigo.empresas_microservicios.service.impl;

import com.codigo.empresas_microservicios.aggregates.response.SunatResponse;
import com.codigo.empresas_microservicios.client.ClientSunat;
import com.codigo.empresas_microservicios.client.SeguridadClient;
import com.codigo.empresas_microservicios.entity.EmpresaEntity;
import com.codigo.empresas_microservicios.repository.EmpresaRepository;
import com.codigo.empresas_microservicios.service.SunatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class SunatServiceImpl implements SunatService {
    @Autowired
    private ClientSunat clientSunat;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SeguridadClient seguridadClient;

    @Value("${token.api}")
    private String token;

    @Override
    public SunatResponse buscarPorRuc(String ruc) {
        SunatResponse sunatResponse = new SunatResponse();
        log.info("Buscando Informacion para el RUC: {}", ruc);

        sunatResponse = execution(ruc);

        return sunatResponse;
    }

    @Override
    public EmpresaEntity buscarEmpresaEnBD(String ruc) {
        return empresaRepository.findByNumeroDocumento(ruc)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con número de documento: " + ruc));
    }

    @Override
    public String resgitrarEmpresa(String ruc, String authHeader) {
        log.info("Buscando Informacion para el RUC: {}", ruc);
        //Aqui Ejecute la consulkta al servicio de reniec
        SunatResponse sunatResponse  = execution(ruc);

        String token = extractToken(authHeader);
        String tokenOk = "Bearer "+token;
        String username = seguridadClient.getInfoSaludo(tokenOk);
        log.info("This is username {}", username);


        if(sunatResponse == null){
            return "No hay empresa con este RUC";
        }

        EmpresaEntity empresaEntity = EmpresaEntity.builder()
                .razonSocial(sunatResponse.getRazonSocial())
                .tipoDocumento(sunatResponse.getTipoDocumento())
                .numeroDocumento(sunatResponse.getNumeroDocumento())
                .estado(sunatResponse.getEstado())
                .condicion(sunatResponse.getCondicion())
                .direccion(sunatResponse.getDireccion())
                .ubigeo(sunatResponse.getUbigeo())
                .viaTipo(sunatResponse.getViaTipo())
                .viaNombre(sunatResponse.getViaNombre())
                .zonaCodigo(sunatResponse.getZonaCodigo())
                .zonaTipo(sunatResponse.getZonaTipo())
                .numero(sunatResponse.getNumero())
                .interior(sunatResponse.getInterior())
                .lote(sunatResponse.getLote())
                .dpto(sunatResponse.getDpto())
                .manzana(sunatResponse.getManzana())
                .kilometro(sunatResponse.getKilometro())
                .distrito(sunatResponse.getDistrito())
                .provincia(sunatResponse.getProvincia())
                .departamento(sunatResponse.getDepartamento())
                .EsAgenteRetencion(false)
                .EsBuenContribuyente(false)
                .localesAnexos(sunatResponse.getLocalesAnexos())
                .tipo(sunatResponse.getTipo())
                .actividadEconomica(sunatResponse.getActividadEconomica())
                .numeroTrabajadores(sunatResponse.getNumeroTrabajadores())
                .tipoFacturacion(sunatResponse.getTipoFacturacion())
                .tipoContabilidad(sunatResponse.getTipoContabilidad())
                .comercioExterior(sunatResponse.getComercioExterior())
                .usuarioRegistro(username)
                .fechaRegistro(sunatResponse.getFechaRegistro())
                .build();

        empresaRepository.save(empresaEntity);


        return "agregado";
    }

    //EJECUTANDO EL API EXTERNA
    private SunatResponse execution(String ruc){
        String tokenOk = "Bearer "+token;
        return clientSunat.getEmpresa(ruc,tokenOk);
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(7); // Removes "Bearer " (7 characters)
    }
}
