package fr.eni.caveavin.repository;

import fr.eni.caveavin.bo.client.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void test_save() {
        System.out.println("\n\nSTART TEST SAVE");
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        int initialSize = clients.size();

        Client newClient = Client.builder()
                .pseudo("Fluffy Unicorn")
                .password("pink")
                .prenom("Laurent")
                .nom("OUTAN")
                .build();

        clientRepository.save(newClient);

        clients = clientRepository.findAll();
        int finalSize = clients.size();

        assertNotNull(newClient.getPseudo());
        assertEquals(initialSize+1, finalSize);
        assertEquals(newClient, clients.getFirst());

        System.out.println(newClient);
        System.out.println("END TEST SAVE\n\n");
    }

    @Test
    public void test_delete() {
        System.out.println("\n\nSTART TEST DELETE");
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients);
        int initialSize = clients.size();
        Client clientToDelete = clients.getFirst();
        clientRepository.delete(clientToDelete);

        clients = clientRepository.findAll();
        int finalSize = clients.size();
        assertEquals(initialSize-1, finalSize);

        Optional<Client> shouldBeEmpty = clientRepository.findById(clientToDelete.getPseudo());
        assertTrue(shouldBeEmpty.isEmpty());

        System.out.println(clientToDelete);
        System.out.println("END TEST DELETE\n\n");
    }
}