package fr.eni.caveavin.repository;

import fr.eni.caveavin.bo.client.Client;
import fr.eni.caveavin.bo.client.Proprietaire;
import fr.eni.caveavin.bo.client.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UtilisateurRepositoryTest {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    ProprietaireRepository proprietaireRepository;

    @Test
    public void findAllUser_shouldReturnAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        System.out.println(utilisateurs);
        assertEquals(9, utilisateurs.size());
    }

    @Test
    public void findAllProprietaire_shouldReturnAllProprietaires() {
        List<Proprietaire> proprietaires = proprietaireRepository.findAll();
        System.out.println(proprietaires);
        assertEquals(3, proprietaires.size());
    }

    @Test
    public void findAllClient_shouldReturnAllClients() {
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        assertEquals(0, clients.size());
    }
}