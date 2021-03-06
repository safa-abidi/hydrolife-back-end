package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_res;
    private Date dateRes;

    private int nbre_personnes_res;

    private Long idClient;
    private String nomClient;
    private String prenomClient;

    private Long idCentre;
    private Long idService;
    private String nomService;

    private double pourcentagePromo;

    private double montant = 0;

//    //relations
//    @ManyToOne
//    @JoinColumn(name = "id_centre") //foreign key
//    private Centre centre;
//
//    @ManyToOne
//    @JoinColumn(name = "id_service")
//    private Services service;
}
