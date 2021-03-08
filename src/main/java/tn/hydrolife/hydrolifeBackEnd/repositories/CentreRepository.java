package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;

import java.util.Optional;

public interface CentreRepository extends JpaRepository<Centre, Long> {
//    Optional<Centre> findCentreById(Long id);
//    Optional<Centre> findCentreByEmail(String email);
}
