package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    //recupere les photos d'un centre donné par id
    List<Photo> findByIdCentre(Long id);
}
