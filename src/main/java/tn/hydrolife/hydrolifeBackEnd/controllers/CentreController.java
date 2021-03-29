package tn.hydrolife.hydrolifeBackEnd.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;

@RestController
@RequestMapping("/api/centre")
public class CentreController {
    private final CentreService centreService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CentreRepository centreRepository;
    @Autowired
    ServletContext context;

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
    public ResponseEntity<Centre> addCentre(@RequestParam("file") MultipartFile file, @RequestParam("centre") String centre) throws JsonProcessingException, Exception {
        Centre centree = new ObjectMapper().readValue(centre, Centre.class);

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

        centree.setProfilePic(newFileName);

        //password encoding
        centree.setPassword(passwordEncoder.encode(centree.getPassword()));

        Centre newCentre = centreService.addCentre(centree);
        return new ResponseEntity<>(newCentre, HttpStatus.CREATED);
    }

    //for profile pic
    @GetMapping("/profilePic/{idCentre}")
    public byte[] getProfilePic(@PathVariable("idCentre") Long idCentre) throws IOException {
        Centre centre = centreRepository.findById(idCentre).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + centre.getProfilePic()));
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

    //trouver tous les adresses des centres
    @GetMapping("/findCentreAdresses")
    public ResponseEntity<List<String>> getAllAdresses() {
        List<String> adresses = centreRepository.findAllAdresses();
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }
}
