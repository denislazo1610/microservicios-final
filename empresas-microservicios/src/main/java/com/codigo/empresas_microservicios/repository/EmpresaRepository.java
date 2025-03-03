package com.codigo.empresas_microservicios.repository;

import com.codigo.empresas_microservicios.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByNumeroDocumento(String numeroDocumento);
}
