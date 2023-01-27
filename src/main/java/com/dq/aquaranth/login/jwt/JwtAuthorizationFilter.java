package com.dq.aquaranth.login.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dq.aquaranth.commons.utils.ResponseUtil;
import com.dq.aquaranth.menu.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.dq.aquaranth.login.jwt.JwtProperties.SECRET;
import static com.dq.aquaranth.login.jwt.JwtProperties.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * 요청 필터당 하나만 존재하는 필터이기 때문에 application 으로 들어오는 모든 요청을 여기에서 가로챕니다.
 * 사용자가 application 자원에 access 권한이 있는지를 논리적으로 처리해줍니다.
 *
 * @author 임종현
 */
@Log4j2
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String[] whitelist = {"/api/login", "/api/token/refresh", "/api/file/**", "/swagger-ui/**"};

    /**
     * access token 의 유효성 검증을 처리합니다.
     * 시큐리티 컨텍스트에 인증시도한 유저정보를 저장합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException  {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (isLoginCheckPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!isToken(authorizationHeader)) {
            log.info("토큰을 찾을 수 없습니다.");
            ResponseUtil.sendError(response, ErrorCodes.TOKEN_MISSING);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            log.info("access token을 검증합니다.");
            String token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes()); // 토큰 생성할 때와 같은 알고리즘으로 풀어야함.
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject(); // token 과 함께 제공되는 사용자 이름을 줍니다.

            // 암호가 필요없는 이유는 token 검증을 끝마쳤기 때문에 이미 유효한 token 으로 인증이 된 사용자이다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | SignatureVerificationException | JWTDecodeException jwtVerificationException) {
            log.info("access toke이 만료되었습니다. => {}", jwtVerificationException.getMessage());
            ResponseUtil.sendError(response, ErrorCodes.ACCESS_TOKEN_EXPIRED);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        log.info("요청주소가 화이트리스트에 등록된 주소인지 확인을 합니다. 요청주소 => {}", requestURI);
        boolean result = PatternMatchUtils.simpleMatch(whitelist, requestURI);
        if (result) {
            log.info("uri {} 는 화이트리스트에 등록된 주소입니다. 인가필터를 통과합니다.", requestURI);
        }
        return result;
    }

    /**
     * request header가 존재하지 않거나, bearer 로 시작하지 않는다면 false 반환
     *
     * @param authorizationHeader reqeust header
     * @return bearer token 존재여부
     */
    private boolean isToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX);
    }
}
