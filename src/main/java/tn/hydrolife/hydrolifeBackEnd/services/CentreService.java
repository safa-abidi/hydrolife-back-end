package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetails;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CentreService {
    @Autowired
    private final CentreRepository centreRepository;

    @Autowired
    public CentreService(CentreRepository centreRepository) {
        this.centreRepository = centreRepository;
    }

    //ajouter un centre
    public Centre addCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    //trouver tous les centres
    public List<Centre> findAllCentres() {
        return centreRepository.findAll();
    }

    //modifier
    public Centre updateCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    //supprimer
    public void deleteCentre(Long id) {
        centreRepository.deleteById(id);
    }

    //trouver un centre avec id
    public Centre findCentre(Long id) {
        return centreRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("User by id " + id + " was not found"));
    }

    //trouver un centre avec email
    public Centre findCentreByEmail(String email) {
        return centreRepository.findByEmail(email)
                .orElseThrow(() -> new HydroLifeException("User by email " + email + " was not found"));
    }

    //GET LOGGED CENTRE INFORMATION
    @Transactional(readOnly = true)
    public Optional<Centre> getCurrentCentre() {
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return centreRepository.findByEmail(principal.getUsername());
    }
}
