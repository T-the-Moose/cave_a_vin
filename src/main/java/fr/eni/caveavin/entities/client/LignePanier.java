package fr.eni.caveavin.entities.client;

import fr.eni.caveavin.entities.vin.Bouteille;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name="cav_line")
public class LignePanier {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="quantity")
    private Integer qte_commandee;
    @Column(name = "price", length = 2, precision = 2)
    private Float prix;

    @ManyToOne
    @JoinColumn(name = "bottle_id")
    private Bouteille bouteille;
}
