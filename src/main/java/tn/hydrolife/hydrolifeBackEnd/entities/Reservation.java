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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_res;
    private Date date_debut_res;
    private Date date_fin_res;
    private int nbre_personnes_res;

    //relations
    @ManyToOne
    @JoinColumn(name="id_client") //foreign key
    private Client client;

    @ManyToOne
    @JoinColumn(name="id_centre") //foreign key
    private Centre centre;

    @OneToMany(mappedBy="reservation")
    Set<Services> services = new HashSet<Services>();
}
