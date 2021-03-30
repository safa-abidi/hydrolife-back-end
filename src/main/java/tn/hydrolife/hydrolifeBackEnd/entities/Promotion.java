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
    private double pourcentage;

    private Long idCentre;

    //relations
    @OneToMany
    Set<Services> services = new HashSet<>();

}
