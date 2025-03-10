package com.codigo.seguridad.repository;

import com.codigo.seguridad.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombreRol(String rol);
}
