package fr.eni.caveavin.entities.client;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString(exclude = {"password"})
@EqualsAndHashCode(of = {"pseudo"})
@DiscriminatorColumn(name = "role")
@Table(name = "cav_user")
@DiscriminatorValue("user")
public class Utilisateur {
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
