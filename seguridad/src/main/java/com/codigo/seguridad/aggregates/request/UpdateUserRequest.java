package com.codigo.seguridad.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private String password;
    private String role;
}
