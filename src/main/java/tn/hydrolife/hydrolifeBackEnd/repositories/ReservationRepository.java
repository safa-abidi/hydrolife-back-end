package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //recupere les reservations d'un client donné par id
    List<Reservation> findByIdClient(Long id);

    //recupere les reservations d'un centre donné par id
    List<Reservation> findByIdCentre(Long id);

    //historique client
    List<Reservation> findByIdClientAndDateResBefore(Long id, Date d);

    //Reservation a venir client
    List<Reservation> findByIdClientAndDateResAfter(Long id, Date d);

    //historique centre
    List<Reservation> findByIdCentreAndDateResBefore(Long id, Date d);

    //Reservation a venir centre
    List<Reservation> findByIdCentreAndDateResAfter(Long id, Date d);

}
