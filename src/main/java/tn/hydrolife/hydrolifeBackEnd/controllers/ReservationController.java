package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.*;
import tn.hydrolife.hydrolifeBackEnd.repositories.ReservationRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.ClientService;
import tn.hydrolife.hydrolifeBackEnd.services.ReservationService;
import tn.hydrolife.hydrolifeBackEnd.services.ServicesService;

import java.util.*;

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

        reservation.setNomService(service.getLibelle_service());

        Centre centre = centreService.findCentre(service.getIdCentre());
        reservation.setIdCentre(centre.getId());

        //get current logged client
        Optional<Client> currentClient = clientService.getCurrentClient();
        //get his id
        Long currentClientId = currentClient.get().getId();
        //set idClient in the reservation
        reservation.setIdClient(currentClientId);

        reservation.setNomClient(currentClient.get().getNom());
        reservation.setPrenomClient(currentClient.get().getPrenom());

        //ajouter la reservation à ce client
        currentClient.get().getReservations().add(reservation);

        //ajouter la reservation à ce service
        service.getReservations().add(reservation);

        //ajouter la reservation à ce centre
        //centre.getReservations().add(reservation);

        //oumour flous
        //tarif mabda2iyan (sec, maghir promotionet)
        double tarif = service.getPrix_service() * reservation.getNbre_personnes_res();
        reservation.setMontant(tarif);

        //ken fama promotion
        //collect that centers promotions fi lista
        Set<Promotion> promotions = centre.getPromotions();

        promotions.forEach((promotion)-> promotion.getServices()
                                            .forEach((serv)->
                                            {
                                                if ((serv.getId_service() == reservation.getIdService())
                                                && (!promotion.getDate_debut_promo().after(reservation.getDateRes())
                                                        && !promotion.getDate_fin_promo().before(reservation.getDateRes()))){
                                                    double pourcentage = promotion.getPourcentage();
                                                    double flous = reservation.getMontant()
                                                            - ( (reservation.getMontant() * pourcentage) / 100);
                                                    reservation.setMontant(flous);
                                                    reservation.setPourcentagePromo(promotion.getPourcentage());
                                                }
                                            }));

        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);

    }
    //modifier une reservation
    @PutMapping("/{idService}/update")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation,
                                                         @PathVariable("idService") Long idService){

        //set the service id in reservation
        reservation.setIdService(idService);

        //get that service and set its centre id in reservation
        Services service = servicesService.findService(idService);

        reservation.setNomService(service.getLibelle_service());

        Centre centre = centreService.findCentre(service.getIdCentre());
        reservation.setIdCentre(centre.getId());

        //montant
        double tarif = service.getPrix_service() * reservation.getNbre_personnes_res();
        reservation.setMontant(tarif);
        //promotions stuff
        Set<Promotion> promotions = centre.getPromotions();

        promotions.forEach((promotion)-> promotion.getServices()
                .forEach((serv)->
                {
                    if ((serv.getId_service() == reservation.getIdService())
                            && (!promotion.getDate_debut_promo().after(reservation.getDateRes())
                            && !promotion.getDate_fin_promo().before(reservation.getDateRes()))){
                        double pourcentage = promotion.getPourcentage();
                        double flous = reservation.getMontant()
                                - ( (reservation.getMontant() * pourcentage) / 100);
                        reservation.setMontant(flous);
                        reservation.setPourcentagePromo(promotion.getPourcentage());
                    }
                }));

        Reservation updateReservation = reservationService.updateReservation(reservation);
        return new ResponseEntity<>(updateReservation, HttpStatus.OK);
    }

    //supprimer une reservation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id){
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //AFFICHAGE DES RESERVATIONS

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

    //OMGGGGGGGGG
    //CLIENT
    //collecter les reservations anciennes d'un meme client par son id
    @GetMapping("/historiqueClient/{idClient}")
    public ResponseEntity<List<Reservation>> getReservationsHistoryByIdClient(@PathVariable("idClient") Long idClient){
        Date date = new Date();
        List<Reservation> reservations = reservationRepository.findByIdClientAndDateResBefore(idClient, date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    //collecter les reservations a venir d'un meme client par son id
    @GetMapping("/aVenirClient/{idClient}")
    public ResponseEntity<List<Reservation>> getUpcomingReservationsByIdClient(@PathVariable("idClient") Long idClient){
        Date date = new Date();
        List<Reservation> reservations = reservationRepository.findByIdClientAndDateResAfter(idClient, date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    //CENTRE
    //collecter les reservations anciennes d'un meme centre par son id
    @GetMapping("/historiqueCentre/{idCentre}")
    public ResponseEntity<List<Reservation>> getReservationsHistoryByIdCentre(@PathVariable("idCentre") Long idCentre){
        Date date = new Date();
        List<Reservation> reservations = reservationRepository.findByIdCentreAndDateResBefore(idCentre, date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    //collecter les reservations a venir d'un meme centre par son id
    @GetMapping("/aVenirCentre/{idCentre}")
    public ResponseEntity<List<Reservation>> getUpcomingReservationsByIdCentre(@PathVariable("idCentre") Long idCentre){
        Date date = new Date();
        List<Reservation> reservations = reservationRepository.findByIdCentreAndDateResAfter(idCentre, date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


//    historique des reservations du client
//    @GetMapping("historiqueClient/{id}")
//    public ResponseEntity<List<Reservation>> getHistoriqueByIdClient(@PathVariable("id") Long id){
//        List<Reservation> reservations = reservationService.findReservationByClient(id);
//
//        List<Reservation> historique = null;
//
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        formatter.format(date);
//
//        reservations.stream()
//                .filter(reservation -> reservation.getDate_res().before(date))
//                .sorted()
//                .forEach(reservation -> historique.add(reservation));
//
//        if (historique != null) {
//            return new ResponseEntity<>(historique, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
}
