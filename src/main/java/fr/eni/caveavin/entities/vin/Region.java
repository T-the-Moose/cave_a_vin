package fr.eni.caveavin.entities.vin;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cav_regions")
public class Region {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", length=250, unique = true)
    private String nom;
}
