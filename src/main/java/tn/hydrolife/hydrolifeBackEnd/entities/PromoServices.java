package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


@IdClass(CleEtrangerePromoService.class)
public class PromoServices {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_service") //foreign key
    private Services services;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_promo") //foreign key
    private Promotion promotion;
}
