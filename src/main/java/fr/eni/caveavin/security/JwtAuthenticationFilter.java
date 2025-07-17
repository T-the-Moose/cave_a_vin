package fr.eni.caveavin.security;

import fr.eni.caveavin.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // Permet de vérifier le token JWT dans les requêtes entrantes
    // HttpServletRequest : Requête entrante
    // HttpServletResponse : Réponse sortante
    // FilterChain : Si oui ou non on peut accèder à la ressource demandée
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        // Vérifie si l'en-tête Authorization est présent et commence par "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // On autorise l'accès à la page de login
            filterChain.doFilter(request, response);
            return;
        }

        // On enlève "Bearer " de devant le token JWT
        final String jwtToken = authorizationHeader.substring(7);
        // Extrait le nom d'utilisateur du token JWT
        final String username = jwtService.extractUsername(jwtToken);

        // Si l'user est renseigné et qu'il n'est pas déjà authentifié dans le contexte de Spring Security
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Vérifie si le token JWT existe dans la base de données
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Si le token JWT est valide
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // On crée une nouvelle authentification
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // On enregistre l'authentification dans le contexte de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
