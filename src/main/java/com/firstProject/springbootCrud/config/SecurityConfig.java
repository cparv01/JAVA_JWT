package com.firstProject.springbootCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf
			    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Store CSRF token in a cookie
				)
		.authorizeHttpRequests(request -> request
	        .requestMatchers("api/employees/", "api/employees/session-info").permitAll() // Allow access to these routes without authentication
	        .anyRequest().authenticated()) // Protect other routes (authentication required)
	    .formLogin(Customizer.withDefaults()) // Enable form login
	    .httpBasic(Customizer.withDefaults()) // Enable basic authentication (for testing with Postman)
	    .sessionManagement(session -> session
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create sessions if needed, otherwise stateless
	    )
	    .logout(logout -> logout
	        .logoutUrl("/logout") // Default logout URL (can customize if needed)
	        .logoutSuccessUrl("/login") // Redirect to login page after successful logout
	        .invalidateHttpSession(true) // Invalidate the session on logout
	        .clearAuthentication(true) // Clear authentication information from the SecurityContext
	        .deleteCookies("JSESSIONID") // Delete the session cookie when the user logs out
	    );

		
		
//		Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//			@Override
//			public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//				customizer.disable();
//			}
//		};
		
		
//		http.csrf(Customizer -> Customizer.disable());
//		http.authorizeHttpRequests(request -> request
//				.requestMatchers("api/employees/", "api/employees/session-info").permitAll()
//				.anyRequest()
//				.authenticated());
//		http.formLogin(Customizer.withDefaults());
//		http.httpBasic(Customizer.withDefaults()); //just for postman		
//		http.sessionManagement(session -> session
//				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		
//		http.csrf(Customizer -> Customizer.disable())
//			.authorizeHttpRequests(request -> request.anyRequest().authenticated())
//			.formLogin(Customizer.withDefaults())
//			.httpBasic(Customizer.withDefaults ())
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//		 
//	}
//	
	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//		provider.setUserDetailsService(UserDetailsService);
//		return provider;
//	}
	
	 
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("kiran")
//				.password("1234")
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("admin")
//				.password("123456")
//				.roles("ADMIN")
//				.build();
//		
//		
//		return new InMemoryUserDetailsManager();
//		
//	}
}
