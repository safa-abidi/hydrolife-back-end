package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
