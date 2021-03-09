package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Photo {
    @Id
    private UUID id_photo;
    private String titre_photo;
    private String description;
    @Column(nullable = false)
    private String url_photo;

    //relations
    @ManyToOne
    @JoinColumn(name="id_centre") //foreign key
    private Centre centre;
}
