package fr.eni.caveavin.repository;

import fr.eni.caveavin.bo.client.Adresse;
import fr.eni.caveavin.bo.client.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @Test
    public void test_save() {
        // Récupération de la liste des clients avant l'ajout
        List<Client> clients = clientRepository.findAll();
        int initialSize = clients.size();

        Client newClient = Client.builder()
                .pseudo("Fluffy Unicorn")
                .password("pink")
                .prenom("Laurent")
                .nom("OUTAN")
                .build();

        clientRepository.save(newClient);

        // Récupération de la liste des clients après l'ajout
        clients = clientRepository.findAll();
        int finalSize = clients.size();

        // Vérification que le client a été ajouté
        assertNotNull(newClient.getPseudo());
        // Vérification que la taille de la liste a augmenté
        assertEquals(initialSize+1, finalSize);
        // Vérification que le nouveau client est bien le premier de la liste
        assertEquals(newClient, clients.getFirst());
    }

    @Test
    public void test_delete() {
        // Récupération de la liste des clients avant la suppression
        List<Client> clients = clientRepository.findAll();
        int initialSize = clients.size();

        // Vérification qu'il y a au moins un client à supprimer
        Client clientToDelete = clients.getFirst();
        clientRepository.delete(clientToDelete);

        // Récupération de la liste des clients après la suppression
        clients = clientRepository.findAll();
        int finalSize = clients.size();

        // Vérification que la taille de la liste a diminuée
        assertEquals(initialSize-1, finalSize);

        // Optional est une classe qui sert de vérification des valeurs
        Optional<Client> shouldBeEmpty = clientRepository.findById(clientToDelete.getPseudo());

        // Vérification que le client supprimé n'existe plus
        assertTrue(shouldBeEmpty.isEmpty());
    }

    @Test
    public void save_withAdresse_souldInsertAdresse() {
        System.out.println("\n\nSTART TEST SAVE ONE TO ONE");
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        int initialSize = clients.size();

        Adresse newAdresse = Adresse.builder()
                .rue("42 Boulevard des licornes")
                .codePostal("31415")
                .ville("Pi")
                .build();

        Client newClient = Client.builder()
                .pseudo("Fluffy Unicorn")
                .password("pink")
                .prenom("Laurent")
                .nom("OUTAN")
                .adresse(newAdresse)
                .build();

        clientRepository.save(newClient);

        clients = clientRepository.findAll();
        int finalSize = clients.size();

        assertEquals(initialSize+1, finalSize);
        Client actual = clientRepository.findById(newClient.getPseudo()).orElseThrow();
        assertEquals(newClient, actual);
        assertNotNull(actual.getAdresse());

        newAdresse.setId(actual.getAdresse().getId());
        assertEquals(newAdresse, actual.getAdresse());

        System.out.println(newClient);
        System.out.println("END TEST SAVE ONE TO ONE\n\n");
    }

    @Test
    public void delete_clientWithAdresse_shouldDeleteAdresse() {
        System.out.println("\n\nSTART TEST DELETE ONE TO ONE");
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        int initialSize = clients.size();
        Client clientToDelete = clients.getFirst();
        Adresse adresseToDelete = clientToDelete.getAdresse();

        Optional<Adresse> shouldExistAdr = adresseRepository.findById(adresseToDelete.getId());
        assertTrue(shouldExistAdr.isPresent());

        clientRepository.delete(clientToDelete);

        clients = clientRepository.findAll();
        int finalSize = clients.size();
        assertEquals(initialSize-1, finalSize);

        Optional<Client> shouldBeEmpty = clientRepository.findById(clientToDelete.getPseudo());
        assertTrue(shouldBeEmpty.isEmpty());

        Optional<Adresse> shouldBeEmptyAdr = adresseRepository.findById(adresseToDelete.getId());
        assertTrue(shouldBeEmptyAdr.isEmpty());

        System.out.println(clientToDelete);
        System.out.println("END TEST DELETE ONE TO ONE\n\n");
    }

    @Test
    public void delete_clientWithoutAdresse_shouldDeleteOrphanAdresse() {
        System.out.println("\n\nSTART TEST DELETE ORPHAN ONE TO ONE");
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        int initialSize = adresseRepository.findAll().size();
        Client clientToDelete = clients.getFirst();
        Adresse adresseToDelete = clientToDelete.getAdresse();

        Optional<Adresse> shouldExistAdr = adresseRepository.findById(adresseToDelete.getId());
        assertTrue(shouldExistAdr.isPresent());

        clientToDelete.setAdresse(null);
        System.out.println("BEFORE DELETION ");
        System.out.println(clientToDelete);
        clientRepository.delete(clientToDelete);

        int finalSize = adresseRepository.findAll().size();
        assertEquals(initialSize-1, finalSize);

        Optional<Client> shouldBeEmpty = clientRepository.findById(clientToDelete.getPseudo());
        assertTrue(shouldBeEmpty.isEmpty());

        Optional<Adresse> shouldBeEmptyAdr = adresseRepository.findById(adresseToDelete.getId());
        assertTrue(shouldBeEmptyAdr.isEmpty());

        System.out.println(clientToDelete);
        System.out.println("END TEST DELETE ORPHAN ONE TO ONE\n\n");
    }
}