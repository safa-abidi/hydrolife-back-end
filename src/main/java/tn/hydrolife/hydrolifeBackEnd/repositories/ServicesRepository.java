package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    //recupere les services d'un centre donné par id
    List<Services> findByIdCentre(Long id);
}
