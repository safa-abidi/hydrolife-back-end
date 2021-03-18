package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
