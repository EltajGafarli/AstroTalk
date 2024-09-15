package com.example.astrotalk.util;

public final class JwtServiceConstants {
    public final static String JWT_SECRET_KEY = "88a920881cb162d917ef47935e108c40b7b0c3927142e7337370bb0cbe117971";
    public static final String JWT_AUTHORITIES_KEY = "AUTH_KEY";
    public static final String JWT_SERVICE_ADMIN = "ADMIN";
    public static final String JWT_SERVICE_USER = "USER";
    public static final String JWT_SERVICE_UNKNOWN = "UNKNOWN";
    public static final Long JWT_EXPIRATION = 86400000L;
    public static final Long JWT_REFRESH_TOKEN_EXPIRATION = 604800000L;
    private JwtServiceConstants() {

    }

}