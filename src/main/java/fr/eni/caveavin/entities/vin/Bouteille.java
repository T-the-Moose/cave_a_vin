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

    @NotBlank() @Size(min=2, max=250)
    @Column(name="name", length=250)
    private String nom;

    @Column(name="sparkling")
    private boolean petillant;

    @Size(min=2, max=100)
    @Column(name="vintage", length=100)
    private String millesime;

    @Min(1)
    @Column(name="quantity")
    private int quantite;

    @Column(name="price", precision=2)
    private float prix;

    @ManyToOne
    @JoinColumn(name="region_id")
    @EqualsAndHashCode.Exclude
    @NotNull
    private Region region;

    @ManyToOne
    @JoinColumn(name="color_id")
    @EqualsAndHashCode.Exclude
    @NotNull
    private Couleur couleur;

}
