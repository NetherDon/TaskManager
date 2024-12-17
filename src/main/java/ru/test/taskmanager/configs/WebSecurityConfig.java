package ru.test.taskmanager.configs;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ru.test.taskmanager.JwtAuthenticationFilter;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.properties.Role;
import ru.test.taskmanager.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
            .csrf((configurer) -> configurer.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/test1").permitAll()
                
                .requestMatchers("/auth/logout").authenticated()
                .requestMatchers("/auth/login").not().authenticated()
                .requestMatchers("/auth/**").permitAll()

                .requestMatchers("/tasks/create").hasAnyAuthority(Role.ADMIN.name())

                .requestMatchers("/user/**").hasAuthority(Role.USER.name())
                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin((login) -> login.disable())
            .logout((logout) -> logout.disable())
            .build();
    }

    @Bean
    public CommandLineRunner addDefaultUser(AuthenticationService authService, DataSource dataSource, PasswordEncoder passwordEncoder) 
    {
        return (args) -> 
        {
            User admin = new User(
                "admin@test.ru", 
                passwordEncoder.encode("12345")
            );
            admin.addRoles(
                Role.ADMIN,
                Role.USER
            );

            if (!authService.isEmailExists(admin.getEmail()))
            {
                authService.registerUser(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception 
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) 
    {
        return new Test(userDetailsService, passwordEncoder);
    }

    public static class Test extends DaoAuthenticationProvider
    {
        public Test(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder)
        {
            super(passwordEncoder);
            this.setUserDetailsService(userDetailsService);
            this.hideUserNotFoundExceptions = false;
        }   
    }
}
