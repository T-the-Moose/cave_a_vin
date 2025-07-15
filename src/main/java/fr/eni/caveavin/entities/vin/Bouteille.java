package fr.eni.caveavin.entities.vin;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cav_bottles")
public class Bouteille {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{exception.bouteille.nom}") @Size(min=1, max=250)
    @Column(name="name", length=250, unique = true)
    private String nom;

    @Column(name="sparkling")
    private boolean petillant;

    @Size(min=1, max=100, message = "{exception.bouteille.millesime}")
    @Column(name="vintage", length=100)
    private String millesime;

    @Min(value = 1, message = "{exception.bouteille.quantite}")
    @Column(name="quantity")
    private int quantite;

    @Min(value = 1, message = "{exception.bouteille.prix}")
    @Column(name="price", precision=2)
    private float prix;

    @ManyToOne
    @JoinColumn(name="region_id")
    @EqualsAndHashCode.Exclude
    @NotNull(message = "{exception.bouteille.region}")
    private Region region;

    @ManyToOne
    @JoinColumn(name="color_id")
    @EqualsAndHashCode.Exclude
    @NotNull(message = "{exception.bouteille.couleur}")
    private Couleur couleur;

}
