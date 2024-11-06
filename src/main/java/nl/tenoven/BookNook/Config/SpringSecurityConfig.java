package nl.tenoven.BookNook.Config;

import nl.tenoven.BookNook.Services.CustomUserDetailsService;
import nl.tenoven.BookNook.filters.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/books/unvalidated",
                                "/authors/unvalidated"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/authors/{authorid}/validate",
                                "/books/{bookid}/validate"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/users/{username}",
                                "/users/{username}/authorities"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/users/{username}",
                                "/books/{bookid}",
                                "/authors/{authorid}",
                                "/reviews/{reviewid}/comments/{commentid}",
                                "/reviews/{reviewid}",
                                "/users/{username}/authorities/{authority}"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/authenticated"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,
                                "/books/{bookid}",
                                "/authors/{authorid}",
                                "/reviews/{reviewid}/comments/{commentid}",
                                "/reviews/{reviewid}",
                                "/users/{username}"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.POST,
                                "/books/{bookId}/addAuthor/{authorId}",
                                "/reviews/{reviewId}/addBook/{bookId}",
                                "/books",
                                "/authors",
                                "/reviews/*/comments",
                                "/reviews",
                                "/books/{bookid}/cover",
                                "/authors/{authorid}/photo",
                                "/users/{username}/picture"
                        ).hasRole("USER")

                        .requestMatchers(HttpMethod.GET,
                                "/books",
                                "/authors",
                                "/reviews/*/comments",
                                "/reviews",
                                "/books/{bookid}",
                                "/authors/{authorid}",
                                "/reviews/{reviewid}/comments/{commentid}",
                                "/reviews/{reviewid}",
                                "/users/{username}",
                                "/books/{bookid}/cover",
                                "/authors/{authorid}/photo",
                                "/users/{username}/picture",
                                "/users/{username}/authorities",
                                "/authenticate"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        // Default policy: Deny all other requests
                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
