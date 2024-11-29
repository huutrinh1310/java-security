package com.example.demo.constants;

public class SecurityConstants {
    public static final String SECURITY_TOKEN_PREFIX = "Bearer ";
    public static final long SECURITY_EXPIRATION_TIME = 864_000_000;
    public static final String SECURITY_HEADER_STRING = "Authorization";
    public static final String SECURITY_SIGN_UP_URL = "/api/user/create";
    public static final String SECURITY_SECRET = "SecretKeyTo";
}
