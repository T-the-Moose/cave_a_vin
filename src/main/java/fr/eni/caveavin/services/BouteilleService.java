package fr.eni.caveavin.services;

import java.util.List;

import fr.eni.caveavin.entities.vin.Bouteille;

public interface BouteilleService {
	List<Bouteille> chargerToutesBouteilles();
	
	Bouteille chargerBouteilleParId(int idBouteille);

	List<Bouteille> chargerBouteillesParRegion(int idRegion);

	List<Bouteille> chargerBouteillesParCouleur(int idCouleur);

	Bouteille ajouterBouteille(Bouteille bouteille);

	void supprimerBouteille(int idBouteille);
}
