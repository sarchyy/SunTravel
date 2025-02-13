package org.example.Security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/admin/destinations/delete/**")
                )
                .authorizeRequests(auth -> auth
                        .requestMatchers("/destinations/view/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/destinations/create/**", "/destinations/delete/**", "/users/delete/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/about", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(handling ->
                        handling.accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h1>Access Denied</h1>");
            response.getWriter().write("<p>You do not have sufficient privileges to perform this action.</p>");
            response.getWriter().write("<a href=\"/\">Go back to the homepage</a>");
            response.getWriter().write("</body></html>");
        };
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var admin = User.withUsername("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN")
                .build();

        var user = User.withUsername("user")
                .password(passwordEncoder.encode("4321"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
