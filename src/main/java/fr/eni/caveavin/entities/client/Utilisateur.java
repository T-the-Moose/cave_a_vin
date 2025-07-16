package fr.eni.caveavin.entities.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@ToString(exclude = {"password"})
@EqualsAndHashCode(of = {"pseudo"})
@DiscriminatorColumn(name = "role")
@Table(name = "cav_users")
@DiscriminatorValue("user")
public class Utilisateur {
    @Id
    @Column(name = "login")
    private String pseudo;

    @Size(min = 8, max = 76)
    @Column(name = "password", length = 76)
    private String password;

    @Size(max = 90)
    @Column(name = "last_name")
    private String nom;

    @Size(max = 150)
    @Column(name = "first_name")
    private String prenom;

    @Size(max = 15)
    @Column(name = "authority", length = 15)
    private String authority;
}
