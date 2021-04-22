package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;
import tn.hydrolife.hydrolifeBackEnd.repositories.PromotionRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.PromotionService;
import tn.hydrolife.hydrolifeBackEnd.services.ServicesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    private final PromotionService promotionService;
    private final CentreService centreService;
    private final ServicesService servicesService;
    private final PromotionRepository promotionRepository;

    //constructor
    public PromotionController(PromotionService promotionService, CentreService centreService, ServicesService servicesService, PromotionRepository promotionRepository) {
        this.promotionService = promotionService;
        this.centreService = centreService;
        this.servicesService = servicesService;
        this.promotionRepository = promotionRepository;
    }

    //trouver tous les promotions
    @GetMapping("/all")
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.finAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    //trouver promotion par son id
    @GetMapping("/find/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable("id") Long id) {
        Promotion promotion = promotionService.findPromotion(id);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    //ajouter une promotion
    @PostMapping("/add")
    public ResponseEntity<Promotion> addPromotion(@RequestBody Promotion promotion) {
        Promotion newPromotion = promotionService.addPromotion(promotion);
        return new ResponseEntity<>(newPromotion, HttpStatus.CREATED);
    }

    //ajouter une promotion à un service donné
    @PostMapping("/{idService}/add")
    public ResponseEntity<Promotion> addPromotionToService(@PathVariable("idService")Long idService, @RequestBody Promotion promotion){
        //getting the logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();

        //getting his id
        Long IdCentre = currentCentre.get().getId();

        //set it in the promotion
        promotion.setIdCentre(IdCentre);

        //nlawej al service with that id
        Services service = servicesService.findService(idService);

        promotion.getServices().add(service);

        //add the promotion to logged centre
        currentCentre.get().getPromotions().add(promotion);

        promotionRepository.save(promotion);
        return new ResponseEntity<>(promotion, HttpStatus.CREATED);

    }

    //modifier une promotion
    @PutMapping("/update")
    public ResponseEntity<Promotion> updatePromotion(@RequestBody Promotion promotion) {
        Promotion updatePromotion = promotionService.updatePromotion(promotion);
        return new ResponseEntity<>(updatePromotion, HttpStatus.OK);
    }

    //supprimer une promotion
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable("id") Long id) {
        promotionService.deletePromotion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //collecter les promotions d'un meme centre par idCentre
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Promotion>> getPromotionsByIdCentre(@PathVariable("id") Long id) {
        List<Promotion> promotions = promotionService.findPromotionByCentre(id);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }
}
