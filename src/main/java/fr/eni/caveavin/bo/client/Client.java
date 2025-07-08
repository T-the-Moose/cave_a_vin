package fr.eni.caveavin.bo.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity @Table(name="cav_client")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString(exclude = {"password"})
@EqualsAndHashCode(of = {"pseudo"})
public class Client {
    @Id
    @Column(name = "login")
    private String pseudo;

    @Column(name = "password")
    private String password;

    @Column(name = "last_name")
    private String nom;

    @Column(name = "first_name")
    private String prenom;
}
