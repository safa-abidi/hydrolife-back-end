package tn.hydrolife.hydrolifeBackEnd.repositories;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;

import java.util.List;
import java.util.Optional;

public interface CentreRepository extends JpaRepository<Centre, Long> {
    Optional<Centre> findByEmail(String email);

    //pour la carte (map)
    @Query(value = "SELECT adresse FROM User u WHERE u.role ='CENTRE'", nativeQuery = true)
    List<String> findAllAdresses();

    //pour la recherche
    @Query("select c from Centre c where nom like %?1% or adresse like %?2%")
    List<Centre> findByNomOrAdresse(@Nullable String nom, @Nullable String adresse);
}
