package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Client;
import tn.hydrolife.hydrolifeBackEnd.entities.Reservation;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;
import tn.hydrolife.hydrolifeBackEnd.repositories.ClientRepository;
import tn.hydrolife.hydrolifeBackEnd.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final CentreRepository centreRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, ClientService clientService, CentreRepository centreRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.centreRepository = centreRepository;
    }
    //ajouter reservation : dans controller

    //recuperer tous les reservations
    public List<Reservation> findAllReservations(){
        return reservationRepository.findAll();
    }

    //modifier
    public Reservation updateReservation(Reservation reservation){
        Optional<Client> currentClient = clientService.getCurrentClient();
        reservation.setIdClient(currentClient.get().getId());
        reservation.setPrenomClient(currentClient.get().getPrenom());
        reservation.setNomClient(currentClient.get().getNom());
        reservation.setIdService(reservation.getIdService());
        return reservationRepository.save(reservation);
    }

    //supprimer par id
    public void deleteReservation(Long id){
        reservationRepository.deleteById(id);
    }

    //trouver reservation par son id
    public Reservation findReservation(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(()-> new HydroLifeException("Reservation by id " +id+ " was not found"));
    }
    //collecter les reservations d'un même client par son idClient
    public List<Reservation> findReservationByClient(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new HydroLifeException("Client with id "+id+" was not found"));
        return reservationRepository.findByIdClient(id);
    }

    //collecter les reservations d'un même centre par son idCentre
    public List<Reservation> findReservationByCentre(Long id){
        Centre centre = centreRepository.findById(id)
                .orElseThrow(()-> new HydroLifeException("centre with id "+id+" was not found"));
        return reservationRepository.findByIdCentre(id);
    }
}
