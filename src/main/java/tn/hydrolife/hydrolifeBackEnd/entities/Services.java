package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//heritage
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_service", discriminatorType = DiscriminatorType.STRING, length = 30)

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

    //relations
    //@ManyToOne
    //@JoinColumn(name="id_centre") //foreign key
    private Long idCentre;

    @OneToMany(mappedBy = "services")
    Set<PromoServices> promoServices = new HashSet<PromoServices>();

    @ManyToOne
    @JoinColumn(name="id_reservation") //foreign key
    private Reservation reservation;
}
