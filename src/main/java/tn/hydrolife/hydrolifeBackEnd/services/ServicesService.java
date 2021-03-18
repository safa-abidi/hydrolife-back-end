package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;
import tn.hydrolife.hydrolifeBackEnd.repositories.ServicesRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;
    private final CentreService centreService;
    private final CentreRepository centreRepository;


    @Autowired
    public ServicesService(ServicesRepository servicesRepository, CentreService centreService, CentreRepository centreRepository, CentreRepository centreRepository1) {
        this.servicesRepository = servicesRepository;
        this.centreService = centreService;
        this.centreRepository = centreRepository1;
    }

    //ajouter un service
    public Services addService(Services service) {
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        service.setIdCentre(currentCentre.get().getId());
        currentCentre.get().getServices().add(service);
        return servicesRepository.save(service);
    }

    //trouver tous les services
    public List<Services> findAllServices() {
        return servicesRepository.findAll();
    }

    //modifier
    public Services updateService(Services service) {
        return servicesRepository.save(service);
    }

    //supprimer
    public void deleteService(Long id) {
        servicesRepository.deleteById(id);
    }

    //trouver service avec id
    public Services findService(long id) {
        return servicesRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("Service by id " + id + " was not found"));
    }

    //collecter les services d'un même centre par id
    public List<Services> findServicesByCentre(Long id){
        Centre centre = centreRepository.findById(id)
                .orElseThrow(()-> new HydroLifeException("centre with id "+id+" was not found"));
        return servicesRepository.findByIdCentre(id);
    }

}
