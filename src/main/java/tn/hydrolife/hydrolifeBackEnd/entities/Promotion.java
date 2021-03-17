package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_promo;
    private String titre_promo;
    private String description_promo;
    private Date date_debut_promo;
    private Date date_fin_promo;

    //relations
    @ManyToOne
    @JoinColumn(name="id_centre") //foreign key
    private Centre centre;

    @OneToMany(mappedBy = "promotion")
    Set<PromoServices> promoServices = new HashSet<PromoServices>();
}
