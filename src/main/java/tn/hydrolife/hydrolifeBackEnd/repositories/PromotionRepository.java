package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    //recupere les promotions d'un centre donné par id
    List<Promotion> findByIdCentre(Long id);
}
