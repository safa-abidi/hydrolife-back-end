package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@DiscriminatorValue("hebergement")

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Chambre extends Services {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long numero_chambre;
    private String type_chambre;
    private String etat_chambre;
}
