package com.dq.aquaranth.commons.config;

import com.dq.aquaranth.commons.utils.JWTUtil;
import com.dq.aquaranth.login.handler.CustomLogoutSuccessHandler;
import com.dq.aquaranth.login.jwt.JwtAuthenticationFilter;
import com.dq.aquaranth.login.jwt.JwtAuthorizationFilter;
import com.dq.aquaranth.login.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // Basic AuthenticationManager and UserDetailService Create
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        /*
           ????????? UsernamePasswordAuthenticationFilter ?????? /login ??? ???????????? ???????????? ?????????,
           ?????? ????????? ????????? ????????? ??????????????? ????????? ??? ????????????.
           ?????? ????????? ???????????? URL ??? ????????? ??? ?????????, ???????????? ????????? ??? ?????? ????????? ?????? ????????? ????????????.
         */
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil);
        authenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ?????? ?????? ??????

                .and()
                .cors().configurationSource(corsConfigurationSource()) // cors custom ??????

                .and()
                .formLogin().disable()

                // ?????? ??????
                .authorizeHttpRequests()
                .anyRequest().permitAll()

                .and()
                .logout()
                .disable()

                .authenticationManager(authenticationManager)
                .addFilter(authenticationFilter) // ????????????
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // ????????????, ?????? ????????? ???????????? ?????? ????????? ?????? ?????? ??????????????? ??????.
//
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler(redisService);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type","Request-URI"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
