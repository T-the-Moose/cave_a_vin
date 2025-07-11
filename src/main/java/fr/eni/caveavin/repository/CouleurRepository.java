package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.vin.Couleur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouleurRepository extends JpaRepository<Couleur, Integer> {
}
