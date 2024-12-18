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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    public static PasswordEncoder passwordEncoder() {
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
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/books/unvalidated",
                                "/authors/unvalidated",
                                "/users/{username}",
                                "/users/{username}/authorities"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
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
                                "/reviews/comments/{commentid}",
                                "/reviews/{reviewid}",
                                "/users/{username}/authorities/{authority}"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                "/authenticated",
                                "/users"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH,
                                "/books/{bookid}",
                                "/authors/{authorid}",
                                "/reviews/comments/{commentid}",
                                "/reviews/{reviewid}",
                                "/users/{username}"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.POST,
                                "/books/{bookId}/addAuthor/{authorId}",
                                "/reviews/{revieuwId}/addBook/{bookId}",
                                "/books",
                                "/authors",
                                "/reviews/comments",
                                "/reviews",
                                "/books/{bookid}/cover",
                                "/authors/{authorid}/photo",
                                "/users/{username}/picture"
                        ).hasRole("USER")

                        .requestMatchers(HttpMethod.GET,
                                "/books",
                                "/authors",
                                "/reviews/comments/{reviewId}",
                                "/reviews",
                                "/books/{bookid}",
                                "/authors/{authorid}",
                                "/reviews/comments/{commentid}",
                                "/reviews/{reviewiId}",
                                "/users/{username}",
                                "/books/{bookid}/cover",
                                "/authors/{authorid}/photo",
                                "/users/{username}/picture"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/users", "/authenticate").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
