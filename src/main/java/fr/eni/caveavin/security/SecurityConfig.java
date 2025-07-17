package fr.eni.caveavin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // On configure la sécurité de l'application
        http.authorizeHttpRequests(auth -> auth
            // Tout le monde peut envoyer le formulaire de connexion
            .requestMatchers("/caveavin/auth").permitAll()

            // Tout le monde peut accéder au stock des bouteilles
            .requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/all").permitAll()

            // Un client peut rechercher un panier par ID,
            .requestMatchers(HttpMethod.GET, "/caveavin/paniers/**").hasAnyRole("CLIENT", "OWNER")
            // consulter les paniers d'un client actif non payé
            .requestMatchers(HttpMethod.GET, "/caveavin/paniers/client/actifs/**").hasAnyRole("CLIENT", "OWNER")
            // les commandes d'un client
            .requestMatchers(HttpMethod.GET, "/caveavin/paniers/client/commandes/**").hasAnyRole("CLIENT", "OWNER")

            // Un client peut ajouter, mettre à jour un panier
            .requestMatchers(HttpMethod.POST, "/caveavin/paniers").hasRole("CLIENT")
            .requestMatchers(HttpMethod.PUT, "/caveavin/paniers").hasRole("CLIENT")

            // Un propriétaire peut ajouter des bouteilles
            .requestMatchers(HttpMethod.POST, "/caveavin/bouteilles/save").hasRole("OWNER")
            // Un propriétaire peut mettre à jour des bouteilles
            .requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles/update/**").hasRole("OWNER")
            // Un propriétaire peut supprimer une bouteille
            .requestMatchers(HttpMethod.DELETE, "/caveavin/bouteilles/delete/**").hasRole("OWNER")

        // Toutes les autres requêtes sont refusées
        .anyRequest().denyAll());

        // Connexion de l'utilisateur
        // Utilisation d'un système d'authentification basé sur JWT
        http.authenticationProvider(authenticationProvider);

        // Ajout du filtre JWT dans la chaîne de filtres de Spring Security
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Désactive la gestion des sessions, car on utilise JWT
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // !!!! Désactive CSRF pour simplifier les tests !!!!
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
