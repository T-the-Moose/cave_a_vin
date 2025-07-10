package fr.eni.caveavin.repository;

import fr.eni.caveavin.bo.client.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,String> {

    // Méthode pour rechercher un utilisateur par son pseudo
    Utilisateur findByPseudo(String pseudo);

    // Méthode pour rechercher un utilisateur par son pseudo et mot de passe
    Utilisateur findByPseudoAndPassword(String pseudo, String password);
}
