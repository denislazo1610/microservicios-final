package com.codigo.empresas_microservicios.aggregates.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SunatResponse {
    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String viaTipo;
    private String viaNombre;
    private String zonaCodigo;
    private String zonaTipo;
    private String numero;
    private String interior;
    private String lote;
    private String dpto;
    private String manzana;
    private String kilometro;
    private String distrito;
    private String provincia;
    private String departamento;
    private boolean EsAgenteRetencion;
    private boolean EsBuenContribuyente;
    private String localesAnexos;
    private String tipo;
    private String actividadEconomica;
    private String numeroTrabajadores;
    private String tipoFacturacion;
    private String tipoContabilidad;
    private String comercioExterior;
    private String usuarioRegistro;
    private String fechaRegistro;
}
