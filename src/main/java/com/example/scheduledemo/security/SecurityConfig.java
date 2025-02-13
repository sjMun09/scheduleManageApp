package com.example.scheduledemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())  //CSRF 보호 비활성화
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))  //기존 세션 유지
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/users/signup", "/api/users/login").permitAll()  //회원가입, 로그인은 허용
                    .anyRequest().authenticated())  //나머지 요청은 인증 필요
                .formLogin(login -> login.disable())  //기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())  //HTTP Basic 인증 비활성화
                .logout(logout -> logout
                    .logoutUrl("/api/users/logout")  //로그아웃 API 추가
                    .deleteCookies("JSESSIONID")  //로그아웃 시 JSESSIONID 삭제
                    .invalidateHttpSession(true));  //세션 무효화

            return http.build();
        }
}