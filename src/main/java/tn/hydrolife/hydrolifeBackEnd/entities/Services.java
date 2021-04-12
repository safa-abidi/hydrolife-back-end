package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_service;
    private String libelle_service;
    private String description_service;
    private double prix_service;

    private Long idCentre;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Reservation> reservations = new HashSet<>();


}
