package fr.eni.caveavin.services;

import java.util.List;

import fr.eni.caveavin.entities.client.Panier;

public interface PanierService {

	Panier chargerPanier(int idPanier);
		
	List<Panier> chargerCommandes(String idclient);

	List<Panier> chargerPaniersNonPayes(String idclient);

	Panier ajouterOuMAJPanier(Panier p);
	
	Panier passerCommande(Panier panier);
}
