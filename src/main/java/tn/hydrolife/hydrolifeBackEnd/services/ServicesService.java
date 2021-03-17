package tn.hydrolife.hydrolifeBackEnd.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.User;
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


    @Autowired
    public ServicesService(ServicesRepository servicesRepository, CentreService centreService, CentreRepository centreRepository) {
        this.servicesRepository = servicesRepository;
        this.centreService = centreService;
    }

    //ajouter un service //did some MESS
    public Services addService(Services service){
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        service.setIdCentre(currentCentre.get().getId());
        return servicesRepository.save(service);
    }

    //trouver tous les services
    public List<Services> findAllServices(){
        return servicesRepository.findAll();
    }

    //modifier
    public Services updateService(Services service){
        return servicesRepository.save(service);
    }

    //supprimer
    public void deleteService(Long id){
        servicesRepository.deleteById(id);
    }

    //trouver service avec id
    public Services findService(long id){
        return servicesRepository.findById(id)
                .orElseThrow(()-> new HydroLifeException("Service by id "+id+" was not found"));
    }

}
