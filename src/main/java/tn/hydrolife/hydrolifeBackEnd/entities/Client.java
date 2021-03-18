package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@DiscriminatorValue("CLIENT")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    private String prenom;
    private Date dateNaissance;

    //relations
    @OneToMany(cascade = CascadeType.ALL)
    Set<Reservation> reservations = new HashSet<Reservation>();
}
