package com.codigo.seguridad.repository;

import com.codigo.seguridad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByRole(String role);
}
