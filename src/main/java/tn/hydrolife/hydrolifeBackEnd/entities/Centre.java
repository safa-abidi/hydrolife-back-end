package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@DiscriminatorValue("CENTRE")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Centre extends User{
    private String description;

    //relations
    @OneToMany(mappedBy="centre")
    Set<Reservation> reservations = new HashSet<Reservation>();

    @OneToMany(mappedBy="centre")
    Set<Photo> photos = new HashSet<Photo>();

    //@OneToMany(mappedBy="centre")
    //Set<Services> services = new HashSet<Services>();

    @OneToMany(mappedBy="centre")
    Set<Promotion> promotions = new HashSet<Promotion>();
}
