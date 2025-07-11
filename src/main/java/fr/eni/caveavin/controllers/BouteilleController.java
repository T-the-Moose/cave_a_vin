package fr.eni.caveavin.controllers;

import fr.eni.caveavin.entities.vin.Bouteille;
import fr.eni.caveavin.services.BouteilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Bouteille>> findAll() {
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

}
