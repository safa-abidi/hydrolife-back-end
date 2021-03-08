package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
}
