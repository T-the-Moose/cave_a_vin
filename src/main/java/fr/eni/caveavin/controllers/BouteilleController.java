package fr.eni.caveavin.controllers;

import fr.eni.caveavin.entities.vin.Bouteille;
import fr.eni.caveavin.services.BouteilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caveavin/bouteilles")
public class BouteilleController {

    private final BouteilleService bouteilleService;

    @Autowired
    public BouteilleController(BouteilleService bouteilleService) {
        this.bouteilleService = bouteilleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bouteille>> getAllBouteilles() {
        // ResponseEntity pour pouvoir renvoyer un code HTTP
        List<Bouteille> listBouteilles = this.bouteilleService.chargerToutesBouteilles();
        if (listBouteilles.isEmpty()) {
            // Si la liste est vide, on renvoie un code 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Sinon, on renvoie la liste avec un code 200 (OK)
            return ResponseEntity.ok(listBouteilles);
        }
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Bouteille> getBouteilleById(@PathVariable int id) {
        Bouteille bouteille;
        try {
            bouteille = bouteilleService.chargerBouteilleParId(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bouteille);
    }

    @GetMapping("/couleur/{id:\\d+}")
    public ResponseEntity<List<Bouteille>> getBouteillesByCouleurId(@PathVariable int id) {
        List<Bouteille> list;
        try {
            list = bouteilleService.chargerBouteillesParCouleur(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/region/{id:\\d+}")
    public ResponseEntity<List<Bouteille>> getBouteillesByRegionId(@PathVariable int id) {
        List<Bouteille> list;
        try {
            list = bouteilleService.chargerBouteillesParRegion(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/save")
    public ResponseEntity<Bouteille> saveBouteille(@Valid @RequestBody Bouteille bouteille) {
        // On vérifie si la bouteille à déjà un ID
        if (bouteille.getId() != null) {
            // Si la bouteille existe déjà, on renvoie un code 409 (Conflict)
            return ResponseEntity.status(409).build();
        }
        Bouteille nouvelleBouteille = bouteilleService.ajouterBouteille(bouteille);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleBouteille);
    }

    @PutMapping("/update/{id:\\d+}")
    public ResponseEntity<Bouteille> updateBouteilleWithId(@PathVariable int id, @Valid @RequestBody Bouteille bouteille) {
        if (bouteille.getId() == null || bouteille.getId() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        if (bouteille.getId() != id)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        var newBouteille = bouteilleService.ajouterBouteille(bouteille);
        return ResponseEntity.ok(newBouteille);
    }

    @DeleteMapping("/delete/{id:\\d+}")
    public ResponseEntity<String> deleteBouteille(@PathVariable int id) {
        try {
           bouteilleService.supprimerBouteille(id);
        } catch (RuntimeException e) {
            // Si l'identifiant n'existe pas ou si la bouteille n'est pas trouvée
            return ResponseEntity.status(406).build();
        }
        return ResponseEntity.ok("Bouteille : "  + id + " supprimé avec succès");
    }
}
