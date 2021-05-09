package tn.hydrolife.hydrolifeBackEnd.repositories;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.hydrolife.hydrolifeBackEnd.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //recupere les reservations d'un client donné par id
    List<Reservation> findByIdClient(Long id);

    //recupere les reservations d'un centre donné par id
    List<Reservation> findByIdCentre(Long id);

    //historique client par son id
    List<Reservation> findByIdClientAndDateResBeforeOrderByDateResDesc(Long id, Date d);

    //historique client par son nom ou prenom
    @Query("select r from Reservation r where ((r.nomClient like %?1% or r.prenomClient like %?2%) or (r.nomClient like %?1% and r.prenomClient like %?2%)) and (r.dateRes < ?3) order by r.dateRes desc")
    List<Reservation> findByNomClientOrPrenomClient(@Nullable String nom, @Nullable String prenom, Date d);

    //Reservation a venir client
    List<Reservation> findByIdClientAndDateResAfterOrderByDateRes(Long id, Date d);

    //historique centre
    List<Reservation> findByIdCentreAndDateResBeforeOrderByDateResDesc(Long id, Date d);

    //Reservation a venir centre
    List<Reservation> findByIdCentreAndDateResAfterOrderByDateRes(Long id, Date d);

}
