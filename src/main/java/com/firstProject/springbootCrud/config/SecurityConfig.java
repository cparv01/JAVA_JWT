package com.firstProject.springbootCrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.firstProject.springbootCrud.security.JwtAuthenticationEntryPoint;
import com.firstProject.springbootCrud.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf -> csrf
		        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Store CSRF token in a cookie with HttpOnly set to false
		    )
		    .authorizeHttpRequests(request -> request
		        .requestMatchers("api/employees/", "api/employees/session-info").permitAll() // Allow access to these routes without authentication
		        .anyRequest().authenticated() // Protect other routes (authentication required)
		    )
		    .formLogin(Customizer.withDefaults()) // Enable form login for authentication (defaults are used here)
		    .httpBasic(Customizer.withDefaults()) // Enable basic authentication (useful for testing with Postman, for example)
		    .sessionManagement(session -> session
		        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Sessions will only be created if necessary (otherwise, stateless)
		    )
		    .logout(logout -> logout
		        .logoutUrl("/logout") // Default logout URL (can customize it if needed)
		        .logoutSuccessUrl("/login") // Redirect to login page after successful logout
		        .invalidateHttpSession(true) // Invalidate the session on logout
		        .clearAuthentication(true) // Clear authentication information from the SecurityContext after logout
		        .deleteCookies("JSESSIONID") // Delete the session cookie when the user logs out
		    )
		    .exceptionHandling(ex -> ex
			        .authenticationEntryPoint(point) // Define an authentication entry point for handling authentication failures
			    )
		    .sessionManagement(session -> session
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Make the session stateless, no sessions will be created
			    )
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("ronak")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("raj")
                .password(passwordEncoder().encode("admin"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}