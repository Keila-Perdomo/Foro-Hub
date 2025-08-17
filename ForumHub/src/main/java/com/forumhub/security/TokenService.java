package com.forumhub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    public String generarToken(String username) {
        Algorithm alg = Algorithm.HMAC256(secret);
        Date ahora = new Date();
        Date exp = new Date(ahora.getTime() + expirationMillis);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(ahora)
                .withExpiresAt(exp)
                .withIssuer("ForumHub")
                .sign(alg);
    }

    public String validarYObtenerUsuario(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        DecodedJWT jwt = JWT.require(alg)
                .withIssuer("ForumHub")
                .build()
                .verify(token);
        return jwt.getSubject();
    }
}
