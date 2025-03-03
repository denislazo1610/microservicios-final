package com.codigo.seguridad.aggregates.constants;

public class Constants {
    public static final Boolean STATUS_ACTIVE = true;
    public static final String CLAVE_AccountNonExpired ="isAccountNonExpired";
    public static final String CLAVE_AccountNonLocked ="isAccountNonLocked";
    public static final String CLAVE_CredentialsNonExpired = "isCredentialsNonExpired";
    public static final String CLAVE_Enabled = "isEnabled";
    public static final String REFRESH = "refresh";
    public static final String ACCESS = "access";
    public static final String ENDPOINTS_PERMIT = "/api/auth/**";
    public static final String ENDPOINTS_ACTUATOR_BUS = "/actuator/busrefresh";

//    public static final String PRUEBA = "/api/users/user/**";

    public static final String ENDPOINTS_USER= "/api/users/user/**";
    public static final String ENDPOINTS_ADMIN = "/api/users/admin/**";
}
