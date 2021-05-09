package tn.hydrolife.hydrolifeBackEnd.repositories;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    //recupere les services d'un centre donné par id
    List<Services> findByIdCentre(Long id);

    //pour la recherche
    @Query("select s from Services s where libelle_service like %?1% or description_service like %?2%")
    List<Services> findByLibelleOrDescription(@Nullable String libelle, @Nullable String description);
}
