package fr.eni.caveavin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Recherche les utilisateurs dans la table "cav_users"
        // Le "1" indique que l'utilisateur est actif (dans un autre cas, ajouter un attribut "enabled" dans l'entité User)
        userDetailsManager.setUsersByUsernameQuery("SELECT login, password, 1 FROM cav_users WHERE login = ?");

        // Recherche les rôles de l'utilisateur dans la table "users"
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT login, authority FROM cav_users WHERE login = ?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // On configure la sécurité de l'application
        http
                .authorizeHttpRequests(auth -> {
                    auth
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
                    .anyRequest().denyAll();
                });

        // Connexion par défaut avec le formulaire Spring Security
        http.httpBasic(Customizer.withDefaults());

        // !!!! Désactive CSRF pour simplifier les tests !!!!
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
