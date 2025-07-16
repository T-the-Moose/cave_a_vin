package fr.eni.caveavin.restrepositories;

import fr.eni.caveavin.entities.vin.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "avis", collectionResourceRel = "avis")
public interface AvisRestRepository extends JpaRepository<Avis,Integer> {
    // Grâce au repository rest, on n'a pas besoin de définir de méthode
    // pour les opérations CRUD de base (findAll, findById, save, delete, etc.)

    // Mais par contre, on peut ajouter des méthodes de recherche personnalisées
    // URL -> /caveavin/avis/search/findByClientPseudo?pseudo=log1
    List<Avis> findByClientPseudo(String pseudo);

    // URL -> /caveavin/avis/search/findByQuantiteGreaterThan?quantite=5
    List<Avis> findByQuantiteGreaterThan(int quantiteCommandee);

    // méthode avec LocalDateTime
    // URL -> /caveavin/avis/search/findByDateBetween?deb=2023-01-01&fin=2023-12-31
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    List<Avis> findByDateBetween(LocalDateTime deb, LocalDateTime fin);
}

