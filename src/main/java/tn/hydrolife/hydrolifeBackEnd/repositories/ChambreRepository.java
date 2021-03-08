package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
