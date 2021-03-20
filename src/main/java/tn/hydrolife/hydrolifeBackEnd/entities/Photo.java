package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_photo;

    //POUR LE CENTRE
    private String titre_photo;
    private String description;

    private Long idCentre;

    //***POUR LE LOGIC***//
    private String name;
    private String type;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

    //relations
//    @ManyToOne
//    @JoinColumn(name = "id_centre") //foreign key
//    private Centre centre;
}
