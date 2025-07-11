package fr.eni.caveavin.repository;

import fr.eni.caveavin.entities.client.Client;
import fr.eni.caveavin.entities.client.LignePanier;
import fr.eni.caveavin.entities.client.Panier;
import fr.eni.caveavin.entities.client.Utilisateur;
import fr.eni.caveavin.entities.vin.Bouteille;
import fr.eni.caveavin.entities.vin.Couleur;
import fr.eni.caveavin.entities.vin.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BouteilleRepositoryTest {

    @Autowired
    private BouteilleRepository bouteilleRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CouleurRepository couleurRepository;
    @Autowired
    private LignePanierRepository lignePanierRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private ClientRepository clientRepository;

    // Test pour save une bouteille avec une couleur et une région valides
    @Test
    public void save_bouteille_shouldInsertBouteilleWithColorAndRegion() {
        int initialBouteilleSize = bouteilleRepository.findAll().size();

        // SELECT * FROM couleur WHERE nom = 'Rouge'
        // Récupération de la couleur "Rouge" depuis la base de données
        Couleur couleurRecuperee = couleurRepository.findAll().stream()
                .filter(c -> c.getNom()
                .equals("Rouge"))
                .findFirst()
                .orElseThrow();

        // Création d'une région
        Region region = Region
                .builder()
                .nom("Bourgogne")
                .build();

        // Création d'une couleur
        Couleur couleur = Couleur
                .builder()
                .nom("Rouge")
                .build();

        // Création d'une bouteille
        Bouteille bouteille = Bouteille
                .builder()
                .nom("Chablis")
                .petillant(false)
                .millesime("2020")
                .quantite(10)
                .prix(15.99f)
                .region(region)
                .couleur(couleur)
                .build();

        // Persistance des entités
        regionRepository.save(region);
        couleurRepository.save(couleur);
        Bouteille bouteilleSaved = bouteilleRepository.save(bouteille);

        // Action
        assertNotNull(bouteilleSaved.getId());
        assertTrue(bouteilleSaved.getId() > 0);

        int finalBouteilleSize = bouteilleRepository.findAll().size();
        assertEquals(initialBouteilleSize + 1, finalBouteilleSize);
        System.out.println(bouteilleSaved);
    }

    // Test pour enregistrer plusieurs bouteilles avec des couleurs et régions similaires
    @Test
    public void save_bouteilles_ShouldInsertBouteillesWithRegionAndColor() {
        List<Couleur> couleurs = couleurRepository.findAll();
        List<Region> regions = regionRepository.findAll();

        int initialBouteilleSize = bouteilleRepository.findAll().size();

        for (int i = 0; i < 5; i++) {
            // Création de bouteille
            Bouteille bouteille = Bouteille.builder()
                    .nom("Bouteille " + (i + 1))
                    .petillant(false)
                    .millesime("2020")
                    .quantite(10)
                    .prix(15.99f)
                    .region(regions.get((i*2) % regions.size()))
                    .couleur(couleurs.get(i % couleurs.size()))
                    .build();

            // Sauvegarde de la bouteille
            bouteilleRepository.save(bouteille);
        }

        // Vérification que les bouteilles ont été enregistrées
        int finalBouteilleSize = bouteilleRepository.findAll().size();
        assertEquals(initialBouteilleSize + 5, finalBouteilleSize);

        List<Bouteille> allBouteille = bouteilleRepository.findAll();
        System.out.println(allBouteille);
    }

    // Test pour vérifier si on supprime une bouteille,
    // cela ne supprime pas la couleur et la région associées
    @Test
    public void test_deleteBouteille_shouldNotDeleteRegionAndCouleur() {
        // Création d'une région
        Region region = Region.builder()
                .nom("Bourgogne")
                .build();
        regionRepository.save(region);

        // Création d'une couleur
        Couleur couleur = Couleur.builder()
                .nom("Rouge")
                .build();
        couleurRepository.save(couleur);

        // Création d'une bouteille
        Bouteille bouteille = Bouteille.builder()
                .nom("Chablis")
                .petillant(false)
                .millesime("2020")
                .quantite(10)
                .prix(15.99f)
                .region(region)
                .couleur(couleur)
                .build();
        Bouteille savedBouteille = bouteilleRepository.save(bouteille);

        // Suppression de la bouteille
        bouteilleRepository.delete(savedBouteille);

        // Vérification que la bouteille a été supprimée
        Optional<Bouteille> deletedBouteille = bouteilleRepository.findById(savedBouteille.getId());
        assertTrue(deletedBouteille.isEmpty());

        // Vérification que la région et la couleur existent toujours
        Optional<Region> regionExistante = regionRepository.findById(region.getId());
        assertTrue(regionExistante.isPresent());

        Optional<Couleur> couleurExistante = couleurRepository.findById(couleur.getId());
        assertTrue(couleurExistante.isPresent());
    }

    // Sauvegarde une ligne associé à sa bouteille
    @Test
    public void save_lignePanier_shouldInsertLigneWithBouteille() {

        LignePanier lignePanier = LignePanier.builder()
                .qte_commandee(2)
                .prix(30.0f)
                .build();

        // Récupération d'une bouteille existante
        Bouteille bouteille1 = bouteilleRepository.findAll().stream()
                .findFirst()
                .orElseThrow();

        // Association de la bouteille à la ligne de panier
        lignePanier.setBouteille(bouteille1);

        // Sauvegarde de la ligne de panier
        LignePanier savedLignePanier = lignePanierRepository.save(lignePanier);

        // Vérification de l'identifiant de la ligne de panier
        assertThat(savedLignePanier.getId()).isGreaterThan(0);
        assertThat(savedLignePanier.getBouteille()).isNotNull();
    }

    // Valide la suppresiion de la ligne mais la conservation de la bouteille
    @Test
    public void delete_lignePanier_shouldNotDeleteBouteille() {
        // Création d'une ligne de panier
        LignePanier lignePanier = LignePanier.builder()
                .qte_commandee(2)
                .prix(30.0f)
                .build();

        // Récupération d'une bouteille existante
        Bouteille bouteille1 = bouteilleRepository.findAll().stream()
                .findFirst()
                .orElseThrow();

        // Association de la bouteille à la ligne de panier
        lignePanier.setBouteille(bouteille1);

        // Sauvegarde de la ligne de panier
        LignePanier savedLignePanier = lignePanierRepository.save(lignePanier);

        // Suppression de la ligne de panier
        lignePanierRepository.delete(savedLignePanier);

        // Vérification que la ligne de panier a été supprimée
        Optional<LignePanier> lignePanierDelete = lignePanierRepository.findById(savedLignePanier.getId());
        assertTrue(lignePanierDelete.isEmpty());

        // Vérification que la bouteille existe toujours
        Optional<Bouteille> existingBouteille = bouteilleRepository.findById(bouteille1.getId());
        assertTrue(existingBouteille.isPresent());
    }

    // Test pour rechercher une bouteille par région
    @Test
    public void findByRegion_shouldReturnbouteillesParRegion() {
        System.out.println(bouteilleRepository.findByRegionNom("Pays de la Loire"));
    }

    // Test pour rechercher un utilisateur par pseudo et mot de passe
    @Test
    public void findByPseudoAndPassword_shouldReturnUtilisateur() {
        // Création d'un utilisateur
        Utilisateur utilisateur1 = Utilisateur
                .builder()
                .pseudo("testUser")
                .password("testPassword")
                .build();
        utilisateurRepository.save(utilisateur1);

        // Action
        Utilisateur utilisateurTrouve1 = utilisateurRepository.findByPseudoAndPassword("testUser", "testPassword");
        Utilisateur utilisateurTrouve2 = utilisateurRepository.findByPseudoAndPassword("log7", "pwd7");

        // Vérification
        assertNotNull(utilisateurTrouve1);
        assertNotNull(utilisateurTrouve2);
        assertEquals("testUser", utilisateurTrouve1.getPseudo());
        System.out.println("Sortie 1");
        System.out.println(utilisateurTrouve1);
        System.out.println("Sortie 2");
        System.out.println(utilisateurTrouve2);
    }

    // Test pour sauvegarder un Panier et son Client associé
    @Test
    public void save_panier_shouldInsertPanierWithClient() {
        // Création d'un client
        Client theClient = Client.builder()
                .pseudo("log10")
                .password("pass10")
                .nom("TERRIEUR")
                .prenom("Alain")
                .build();

        // Création d'un panier
        Panier panier = Panier
                .builder()
                .numCommande("CMD12345")
                .paye(false)
                .prixTotal(100.0f)
                .client(theClient)
                .build();

        Panier savedPanier = panierRepository.save(panier);

        // Vérification de l'identifiant du panier
        assertThat(savedPanier.getId()).isGreaterThan(0);
        assertThat(savedPanier.getClient()).isNotNull();
    }

    // Test de sauvegarde de plusieurs paniers d'un même client
    @Test
    public void save_multiplePaniers_shouldInsertMultiplePaniersForClient() {
        // Création d'un client
        Client clientFinal = Client
                .builder()
                .pseudo("log10")
                .password("pass10")
                .nom("TERRIEUR")
                .prenom("Alain")
                .build();

        // Création de plusieurs paniers pour le même client
        for (int i = 0; i < 3; i++) {
            Panier panier = Panier
                    .builder()
                    .numCommande("CMD" + (i + 1))
                    .paye(false)
                    .prixTotal(50.0f * (i + 1))
                    .client(clientFinal)
                    .build();
            panierRepository.save(panier);
        }

        // Vérification que le client a plusieurs paniers
        Optional<Client> shouldExist = clientRepository.findById("log10");
        assertTrue(shouldExist.isPresent());
    }
}