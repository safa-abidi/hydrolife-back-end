package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;
import tn.hydrolife.hydrolifeBackEnd.services.PromotionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    //constructor
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    //trouver tous les promotions
    @GetMapping("/all")
    public ResponseEntity<List<Promotion>> getAllPromotions(){
        List<Promotion> promotions = promotionService.finAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    //trouver promotion par son id
    @GetMapping("/find/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable("id") Long id){
        Promotion promotion = promotionService.findPromotion(id);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    //ajouter une promotion
    @PostMapping("/add")
    public ResponseEntity<Promotion> addPromotion(@RequestBody Promotion promotion){
        Promotion newPromotion = promotionService.addPromotion(promotion);
        return new ResponseEntity<>(newPromotion, HttpStatus.CREATED);
    }

    //modifier une promotion
    @PutMapping("/update")
    public ResponseEntity<Promotion> updatePromotion(@RequestBody Promotion promotion){
        Promotion updatePromotion = promotionService.updatePromotion(promotion);
        return new ResponseEntity<>(updatePromotion, HttpStatus.OK);
    }

    //supprimer une promotion
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable("id") Long id){
        promotionService.deletePromotion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //collecter les promotions d'un meme centre par idCentre
    @GetMapping("findbycentre/{id}")
    public ResponseEntity<List<Promotion>> getPromotionsByIdCentre(@PathVariable("id") Long id){
        List<Promotion> promotions = promotionService.findPromotionByCentre(id);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }
}
