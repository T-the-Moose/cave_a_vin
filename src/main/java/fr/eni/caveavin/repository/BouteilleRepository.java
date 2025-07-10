package fr.eni.caveavin.repository;

import fr.eni.caveavin.bo.vin.Bouteille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {

    // Méthode pour rechercher des bouteilles par régions
    List<Bouteille> findByRegionNom(String nom);

    // Méthode pour rechercher des bouteilles par couleurs
    List<Bouteille> findByCouleurNom(String nom);
}
