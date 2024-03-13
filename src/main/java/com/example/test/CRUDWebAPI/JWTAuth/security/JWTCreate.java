package com.example.test.CRUDWebAPI.JWTAuth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTCreate {
    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMonths(1).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("com.example.test.CRUDWebAPI")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC384("secret"));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC384("secret"))
                .withSubject("User details")
                .withIssuer("com.example.test.CRUDWebAPI")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
