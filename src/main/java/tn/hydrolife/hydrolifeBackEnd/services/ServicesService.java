package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.ServicesRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;

import java.util.List;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    //ajouter un service
    public Services addService(Services service){
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
