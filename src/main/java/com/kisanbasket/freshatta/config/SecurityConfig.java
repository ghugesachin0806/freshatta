package com.kisanbasket.freshatta.config;

import com.kisanbasket.freshatta.enumtype.UserType;
import com.kisanbasket.freshatta.filter.JWTRefreshFilter;
import com.kisanbasket.freshatta.filter.JWTValidationFilter;
import com.kisanbasket.freshatta.utils.JWTUtil;
import com.kisanbasket.freshatta.utils.auth.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider(), jwtAuthenticationProvider()));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //Validation filter for checking JWT in every request
        JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager());

        // refresh token filter
        JWTRefreshFilter jwtRefreshFilter = new JWTRefreshFilter(authenticationManager(), jwtUtil);

        return httpSecurity.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/register", "auth/generate-otp")
                                .permitAll()
                                .requestMatchers("/admin/**").hasRole(UserType.ADMIN.toString())
                                .anyRequest()
                                .authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
