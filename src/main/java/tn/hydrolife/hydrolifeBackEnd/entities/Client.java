package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@DiscriminatorValue("client")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    private String prenom;
    private Date dateNaissance;
}
