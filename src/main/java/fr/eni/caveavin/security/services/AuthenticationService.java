package fr.eni.caveavin.security.services;

import fr.eni.caveavin.entities.client.Utilisateur;
import fr.eni.caveavin.repository.UtilisateurRepository;
import fr.eni.caveavin.security.controllers.AuthenticationRequest;
import fr.eni.caveavin.security.controllers.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private UtilisateurRepository utilisateurRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthenticationService
    (
    UtilisateurRepository utilisateurRepository,
    AuthenticationManager authenticationManager,
    JwtService jwtService
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        // On vérifie les identifiants de l'utilisateur et le password (Essaie d'authentifier l'utilisateur)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getPseudo(),
                        authenticationRequest.getPassword()
                )
        );

        Utilisateur utilisateur = utilisateurRepository.findByPseudo(authenticationRequest.getPseudo());
        String jwtToken = jwtService.generateToken(utilisateur);
        return new AuthenticationResponse(jwtToken);

    }

}
