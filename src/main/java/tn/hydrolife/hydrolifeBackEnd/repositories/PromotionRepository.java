package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;

import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
}
