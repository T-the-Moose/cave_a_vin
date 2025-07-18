package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.client.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, String> {

}
