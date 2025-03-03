package com.codigo.seguridad.aggregates.request;

import com.codigo.seguridad.entity.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String role;
}
