package fr.eni.caveavin.bo.client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "cav_owner")
@DiscriminatorValue("owner")
public class Proprietaire extends Utilisateur {
    @Column(name = "client_number", length = 30)
    private String siret;
}
