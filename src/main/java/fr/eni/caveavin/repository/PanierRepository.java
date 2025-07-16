package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.client.Client;
import fr.eni.caveavin.entities.client.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Integer> {

    // ***********  Les paniers d'un client.
    List<Panier> findByClientPseudoAndNumCommandeIsNull(String pseudo);

    @Query("SELECT p FROM Panier p WHERE p.numCommande IS NULL AND p.client.pseudo = :pseudo")
    List<Panier> findByClientPseudoAndNumCommandeIsNullButWithJPQL(String pseudo);

    // ***********  Les commandes d'un client.
    List<Panier> findByClientPseudoAndNumCommandeIsNotNull(String pseudo);

    @Query(value = "SELECT * FROM cav_shopping_cart p WHERE p.order_number IS NOT NULL AND p.client_id = :pseudo", nativeQuery = true)
    List<Panier> findByClientPseudoAndNumCommandeIsNotNullButWithJPQL(String pseudo);

    @Query(value = "SELECT p.* FROM CAV_SHOPPING_CART p WHERE p.CLIENT_ID = :idClient AND p.ORDER_NUMBER IS NOT NULL", nativeQuery = true)
    List<Panier> findCommandesWithSQL(@Param("idClient") String idClient);

    List<Panier> findByNumCommandeNullAndClient(@Param("client") Client client);
}
