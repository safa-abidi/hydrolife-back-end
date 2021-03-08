package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("centre")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Centre extends User{
    private String description;
}
