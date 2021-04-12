package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Client;
import tn.hydrolife.hydrolifeBackEnd.entities.Reservation;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;
import tn.hydrolife.hydrolifeBackEnd.repositories.ReservationRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.ClientService;
import tn.hydrolife.hydrolifeBackEnd.services.ReservationService;
import tn.hydrolife.hydrolifeBackEnd.services.ServicesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final ServicesService servicesService;
    private final ClientService clientService;
    private final CentreService centreService;
    private final ReservationRepository reservationRepository;

    //constructeur
    public ReservationController(ReservationService reservationService, ServicesService servicesService, ClientService clientService, CentreService centreService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.servicesService = servicesService;
        this.clientService = clientService;
        this.centreService = centreService;
        this.reservationRepository = reservationRepository;
    }

    //trouver tous les reservations
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    //trouver reserva par son id
    @GetMapping("/find/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.findReservation(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    //ajouter une reservation
    @PostMapping("/{idService}/add")
    public ResponseEntity<Reservation> addReservation(
            @PathVariable("idService") Long idService,
            @RequestBody Reservation reservation
    ) {
        //set the service id in reservation
        reservation.setIdService(idService);

        //get that service and set its centre id in reservation
        Services service = servicesService.findService(idService);

        Centre centre = centreService.findCentre(service.getIdCentre());
        reservation.setIdCentre(centre.getId());

        //get current logged client
        Optional<Client> currentClient = clientService.getCurrentClient();
        //get his id
        Long currentClientId = currentClient.get().getId();
        //set idClient in the reservation
        reservation.setIdClient(currentClientId);

        //ajouter la reservation à ce client
        currentClient.get().getReservations().add(reservation);

        //ajouter la reservation à ce service
        service.getReservations().add(reservation);

        //ajouter la reservation à ce centre
        centre.getReservations().add(reservation);

        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);

    }
    //modifier une reservation
    @PutMapping("/update")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation){
        Reservation updateReservation = reservationService.updateReservation(reservation);
        return new ResponseEntity<>(updateReservation, HttpStatus.OK);
    }

    //supprimer une reservation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id){
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //collecter les reservations d'un meme client par son id
    @GetMapping("/findbyclient/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByIdClient(@PathVariable("id") Long id){
        List<Reservation> reservations = reservationService.findReservationByClient(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    //collecter les reservations d'un meme centre par son id
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByIdCentre(@PathVariable("id") Long id){
        List<Reservation> reservations = reservationService.findReservationByCentre(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
