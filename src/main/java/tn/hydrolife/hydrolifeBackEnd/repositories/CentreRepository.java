package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;

import java.util.List;
import java.util.Optional;

public interface CentreRepository extends JpaRepository<Centre, Long> {
    Optional<Centre> findByEmail(String email);

    @Query(value = "SELECT adresse FROM User u WHERE u.role ='CENTRE'", nativeQuery = true)
    List<String> findAllAdresses();
}
