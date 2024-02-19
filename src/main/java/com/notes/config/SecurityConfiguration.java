package com.notes.config;

import com.notes.jwt.JwtService;
import com.notes.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Bean
  @SneakyThrows
  public SecurityFilterChain securityConfig(final HttpSecurity httpSecurity) {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        // No session will be created or used by Spring Security
        .sessionManagement(
            configurer ->
                configurer.
                    sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            configurer ->
                configurer
                    .requestMatchers("/api/v1/notes/**")
                    .authenticated()
                    .requestMatchers("/api/v1/images/**")
                    .authenticated()
                    .requestMatchers("/api/v1/auth/**")
                    .permitAll())
        .addFilterBefore(
            new JwtTokenFilter(jwtService, userDetailsService),
            UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  @SneakyThrows
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authConfiguration) {
    return authConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
