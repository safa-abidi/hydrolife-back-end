package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Promotion;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;
import tn.hydrolife.hydrolifeBackEnd.repositories.PromotionRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final CentreService centreService;
    private final CentreRepository centreRepository;
    private final ServicesService servicesService;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository, CentreService centreService, CentreRepository centreRepository, ServicesService servicesService) {
        this.promotionRepository = promotionRepository;
        this.centreService = centreService;
        this.centreRepository = centreRepository;
        this.servicesService = servicesService;
    }

    //ajouter une promotion //big mess XD
    public Promotion addPromotion(Promotion promotion) {
        //getting the logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();

        //getting his id
        Long IdCentre = currentCentre.get().getId();

        //set it in the promotion
        promotion.setIdCentre(IdCentre);

        Set<Services> currentCentreServices = currentCentre.get().getServices();

        currentCentreServices.forEach((service) -> {
            service.setIdPromo(promotion.getId_promo());
            promotion.getServices().add(service);
        });

        //add the promotion to logged centre
        currentCentre.get().getPromotions().add(promotion);

        return promotionRepository.save(promotion);

    }

    //trouver tous les promotions
    public List<Promotion> finAllPromotions() {
        return promotionRepository.findAll();
    }

    //modifier
    public Promotion updatePromotion(Promotion promotion) {
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        promotion.setIdCentre(currentCentre.get().getId());
        return promotionRepository.save(promotion);
    }

    //supprimer par id
    public void deletePromotion(Long id) {

        //getting the logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();

        //getting his id
        Long IdCentre = currentCentre.get().getId();

        Set<Services> currentCentreServices = currentCentre.get().getServices();
        currentCentreServices.forEach((service) -> service.setIdPromo(null));

        promotionRepository.deleteById(id);
    }

    //trouver promotion par son id
    public Promotion findPromotion(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("Promotion with id " + id + " was not found"));
    }

    //collecter les promotions d'un meme centre par son idCentre
    public List<Promotion> findPromotionByCentre(Long id) {

        Centre centre = centreRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("centre was with id " + id + " not found"));
        return promotionRepository.findByIdCentre(id);
    }

}
