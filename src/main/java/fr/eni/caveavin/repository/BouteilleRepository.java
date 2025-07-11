package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.vin.Bouteille;
import fr.eni.caveavin.entities.vin.Couleur;
import fr.eni.caveavin.entities.vin.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {

    // Méthode pour rechercher des bouteilles par régions
    List<Bouteille> findByRegionNom(String nom);

    // Méthode pour rechercher des bouteilles par région
    List<Bouteille> findByRegion(Region region);

    // Méthode pour rechercher des bouteilles par couleur
    List<Bouteille> findByCouleur(Couleur couleur);

    // Méthode pour rechercher des bouteilles par couleurs
    List<Bouteille> findByCouleurNom(String nom);

    // Méthode pour rechercher des bouteilles par nom
    List<Bouteille> findByNom(@Param("nom") String nom);
}
