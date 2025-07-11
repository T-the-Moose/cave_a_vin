package fr.eni.caveavin.entities.client;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name="cav_shopping_cart")
public class Panier {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", length = 200)
    private String numCommande;

    @Column(name = "total_price", precision = 2)
    private Float prixTotal;

    @Column(name= "paid")
    private Boolean paye;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id")
    private List<LignePanier> lignesPanier;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
}
