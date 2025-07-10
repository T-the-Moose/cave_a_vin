package fr.eni.caveavin.bo.client;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cav_client")
@DiscriminatorValue("client")
public class Client extends Utilisateur {

    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Adresse adresse;
}