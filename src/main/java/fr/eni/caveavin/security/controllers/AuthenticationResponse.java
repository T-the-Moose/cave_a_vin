package fr.eni.caveavin.security.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class AuthenticationResponse {
    private String token;
}
