package fr.eni.caveavin.security.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "pseudo")
public class AuthenticationRequest {

    // Conditionne le body de la requête d'authentification
    // Exemple :
    // {
    //      "pseudo": "user",
    //      "password": "password"
    // }
    private String pseudo;
    private String password;
}
