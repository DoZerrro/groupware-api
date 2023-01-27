package com.dq.aquaranth.commons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dq.aquaranth.login.jwt.JwtProperties.*;

@Component
@Log4j2
public class JWTUtil {
    Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes()); // 토큰 생성할 때와 같은 알고리즘으로 풀어야함.

    public Map<String, String> generateToken(String username) {
        log.info("token을 생성합니다.");
        String accessToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    public Map<String, Object> validateToken(String token)throws JwtException {
        log.info("token을 검증합니다.");
//        Map<String, Object> claim;
//
//        claim = Jwts.parser()
//                .setSigningKey(SECRET.getBytes()) // Set Key
//                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
//                .getBody();
        return null;
    }
}
