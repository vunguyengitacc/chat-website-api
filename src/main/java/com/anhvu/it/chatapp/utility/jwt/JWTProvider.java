package com.anhvu.it.chatapp.utility.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTProvider {
    private String secret = "asiuycrhiomnexi";
    private Long nextExpired;
    private Long nextRefreshExpired;
    private String data;
    private String issuer;
    private String token;
    private Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

    public String generate() {

        String access_token = JWT.create()
                .withSubject(this.data)
                .withExpiresAt(new Date(System.currentTimeMillis() + this.nextExpired))
                .withIssuer(issuer)
                .sign(algorithm);
        this.token = access_token;
        return access_token;
    }

    public String verify() {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(this.token);
        this.data = decodedJWT.getSubject();
        return this.data;
    }
}
