package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.exceptions.UserNotFoundException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;

import java.util.List;

@Service
public class CentreService {
    private final CentreRepository centreRepository;

    @Autowired
    public CentreService(CentreRepository centreRepository) {
        this.centreRepository = centreRepository;
    }

    //ajouter un centre
    public Centre addCentre(Centre centre){
        return centreRepository.save(centre);
    }

    //trouver tous les centres
    public List<Centre> findAllCentres(){
        return centreRepository.findAll();
    }

    //modifier
    public Centre updateCentre(Centre centre){
        return centreRepository.save(centre);
    }

    //supprimer
    public void deleteCentre(Long id){
        centreRepository.deleteById(id);
    }

    //trouver un centre avec id
    public Centre findCentre(Long id){
        return centreRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User by id "+id+" was not found"));
    }

    //trouver un centre avec email
    public Centre findCentreByEmail(String email){
        return centreRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User by id "+email+" was not found"));
    }
}
