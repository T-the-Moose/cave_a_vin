package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.client.Client;
import fr.eni.caveavin.entities.client.Panier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PanierRepositoryTest {
    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BouteilleRepository bouteilleRepository;

    @Test
    public void save_1panier_insertPanierAndClient() {
        Client client = Client.builder()
                .pseudo("log10")
                .password("pass10")
                .nom("TERRIEUR")
                .prenom("Alain")
                .build();

        Panier panier = Panier.builder()
                .numCommande("C012345")
                .paye(true)
                .prixTotal(123F)
                .client(client)
                .build();

        Panier result = panierRepository.save(panier);

        assertNotNull(result.getId());
        assertTrue(result.getId() > 0);

        Optional<Client> shouldExist = clientRepository.findById("log10");
        assertTrue(shouldExist.isPresent());
    }

    @Test
    public void save_multiplePaniers_insertPaniersAnd1Client() {
        Client client = Client.builder()
                .pseudo("log10")
                .password("pass10")
                .nom("TERRIEUR")
                .prenom("Alain")
                .build();

        for (int i = 0; i < 10; i++) {
            Panier panier = Panier.builder()
                    .numCommande("C0123" + i)
                    .paye(true)
                    .prixTotal(123F)
                    .client(client)
                    .build();

            Panier result = panierRepository.save(panier);

            assertNotNull(result.getId());
            assertTrue(result.getId() > 0);
        }

        Optional<Client> shouldExist = clientRepository.findById("log10");
        assertTrue(shouldExist.isPresent());
    }


    public void delete_client_keepsPanier() {
        Client client = Client.builder()
                .pseudo("log10")
                .password("pass10")
                .nom("TERRIEUR")
                .prenom("Alain")
                .build();

        Panier panier = Panier.builder()
                .numCommande("C012345")
                .paye(true)
                .prixTotal(123F)
                .client(client)
                .build();

        Panier result = panierRepository.save(panier);
        panierRepository.flush();

        clientRepository.delete(client);

        Optional<Client> shouldBeEmpty = clientRepository.findById("log10");
        assertTrue(shouldBeEmpty.isEmpty());

        Optional<Panier> shouldExist = panierRepository.findById(result.getId());
        assertTrue(shouldExist.isPresent());
    }

    @Test
    public void test_select() {
        System.out.println(panierRepository.findByClientPseudoAndNumCommandeIsNull("log1"));
        System.out.println(panierRepository.findByClientPseudoAndNumCommandeIsNullButWithJPQL("log1"));

        System.out.println(panierRepository.findByClientPseudoAndNumCommandeIsNotNull("log1"));
        System.out.println(panierRepository.findByClientPseudoAndNumCommandeIsNotNullButWithJPQL("log1"));
    }
}