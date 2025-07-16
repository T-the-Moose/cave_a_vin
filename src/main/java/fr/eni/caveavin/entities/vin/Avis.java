package fr.eni.caveavin.entities.vin;

import fr.eni.caveavin.entities.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cav_avis")
public class Avis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_avis")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "bouteille_id")
    @NotNull
    private Bouteille bouteilleId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "quantity")
    private Integer quantite;

    @Column(name = "note")
    @Min(1) @Max(5)
    private int note;

    @Column (name = "commentaire", length = 500)
    private String commentaire;
}
