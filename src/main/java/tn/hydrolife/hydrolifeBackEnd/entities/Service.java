package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

//heritage
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_service", discriminatorType = DiscriminatorType.STRING, length = 10)

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Service {
    @Id
    private UUID id_service;
    private String libelle_service;
    private String description_service;
    private double prix_service;
}
