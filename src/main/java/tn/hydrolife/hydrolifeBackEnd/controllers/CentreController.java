package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;

import java.util.List;

@RestController
@RequestMapping("/api/centre")
public class CentreController {
    private final CentreService centreService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //constructor
    public CentreController(CentreService centreService) {
        this.centreService = centreService;
    }

    //trouver tous les centres
    @GetMapping("/all")
    public ResponseEntity<List<Centre>> getAllCentres() {
        List<Centre> centres = centreService.findAllCentres();
        return new ResponseEntity<>(centres, HttpStatus.OK);
    }

    //trouver un centre avec id
    @GetMapping("/find/{id}")
    public ResponseEntity<Centre> getCentreById(@PathVariable("id") Long id) {
        Centre centre = centreService.findCentre(id);
        return new ResponseEntity<>(centre, HttpStatus.OK);
    }


    //trouver un centre avec email
    @GetMapping("/get/{email}")
    public ResponseEntity<Centre> getCentreByEmail(@PathVariable("email") String email) {
        Centre centre = centreService.findCentreByEmail(email);
        return new ResponseEntity<>(centre, HttpStatus.OK);
    }

    //ajouter un centre
    @PostMapping("/add")
    public ResponseEntity<Centre> addCentre(@RequestBody Centre centre) {
        //password encoding
        centre.setPassword(passwordEncoder.encode(centre.getPassword()));

        Centre newCentre = centreService.addCentre(centre);
        return new ResponseEntity<>(newCentre, HttpStatus.CREATED);
    }

    //modifier un centre
    @PutMapping("/update")
    public ResponseEntity<Centre> updateCentre(@RequestBody Centre centre) {
        //password encoding
        centre.setPassword(passwordEncoder.encode(centre.getPassword()));

        Centre updateCentre = centreService.updateCentre(centre);
        return new ResponseEntity<>(updateCentre, HttpStatus.OK);
    }

    //supprimer un centre
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCentre(@PathVariable("id") Long id) {
        centreService.deleteCentre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
