package com.oracle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/*@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/public/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/reports/monthly").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/reports/export")
                        .hasAuthority("REPORT_EXPORT")
                        .anyRequest().authenticated()).formLogin(Customizer.withDefaults())
                .logout(logout->logout.logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    UserDetailsService users(PasswordEncoder passwordEncoder) {

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .authorities("ROLE_ADMIN","REPORT_EXPORT")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}*/


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PersistentTokenRepository tokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setJdbcTemplate(new JdbcTemplate(dataSource));
        return repository;
    }

    @Bean
    SecurityFilterChain webSecurity(
            HttpSecurity http,
            PersistentTokenRepository tokenRepository) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/register"))
                .formLogin(Customizer.withDefaults())
                .rememberMe(remember -> remember
                        .tokenRepository(tokenRepository)
                        .key("demo-remember-me-key")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                )
                .logout(Customizer.withDefaults());

        return http.build();
    }
}