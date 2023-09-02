package io.zero.medium.domain.jwt;

public interface JWTDeserializer {
    JWTPayload jwtPayloadFromJWT(String jwtToken);
}
