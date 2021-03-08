package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Promotion {
    @Id
    private UUID id_promo;
    private String titre_promo;
    private String description_promo;
    private Date date_debut_promo;
    private Date date_fin_promo;

    //relations
    @ManyToOne
    @JoinColumn(name="id_centre") //foreign key
    private Centre centre;

    @OneToMany(mappedBy = "promotion")
    Set<PromoService> promoServices = new HashSet<PromoService>();
}
