package com.tuoguan.pickup.config;

import com.tuoguan.pickup.security.CustomAccessDeniedHandler;
import com.tuoguan.pickup.security.CustomAuthenticationEntryPoint;
import com.tuoguan.pickup.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // OPTIONS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 登录
                        .requestMatchers("/api/auth/login").permitAll()

                        // ===== 老师权限 =====

                        .requestMatchers("/api/tasks/today")
                        .hasAnyRole("TEACHER", "ADMIN")

                        .requestMatchers("/api/events/confirm")
                        .hasAnyRole("TEACHER", "ADMIN")

                        .requestMatchers("/api/events/scan")
                        .hasAnyRole("TEACHER", "ADMIN")

                        // ===== 管理后台权限 =====

                        .requestMatchers("/api/students/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/guardians/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/teachers/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/enrollments/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/pickup-tasks/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/attendance-exceptions/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/system-settings/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/notifications/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/event-logs/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/cards/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/dashboard/**")
                        .hasRole("ADMIN")

                        // 其他请求
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}