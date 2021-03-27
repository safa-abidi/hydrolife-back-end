package tn.hydrolife.hydrolifeBackEnd.controllers;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Photo;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;
import tn.hydrolife.hydrolifeBackEnd.repositories.PhotoRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    CentreService centreService;
    @Autowired
    ServletContext context;
    @Autowired
    CentreRepository centreRepository;

    @PostMapping("/add")
    public ResponseEntity<Photo> createDocument(@RequestParam("file") MultipartFile file,
                                                @RequestParam("photo") String photo) throws JsonParseException, JsonMappingException, Exception {
        Photo photoo = new ObjectMapper().readValue(photo, Photo.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("webapp/Images/")).mkdir();
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        photoo.setFileName(newFileName);

        //get current logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        //get his id
        Long currentCentreId = currentCentre.get().getId();

        //set idCentre in the photo
        photoo.setIdCentre(currentCentreId);

        //l'ajouter à ce centre
        currentCentre.get().getPhotos().add(photoo);

        Photo savePhoto = photoRepository.save(photoo);
        if (savePhoto != null) {
            return new ResponseEntity<>(savePhoto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //supprimer une photo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) {
        photoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //collecter les photos d'un centre
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Photo>> getPhotosByIdCentre(@PathVariable("id") Long id) throws Exception {
        centreRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("centre with id " + id + " was not found"));
        List<Photo> photos = photoRepository.findByIdCentre(id);

        photos.forEach((photo) ->
        {
            try {
                Files.readAllBytes(Paths.get(context.getRealPath("/Images") + photo.getFileName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    //collecter tous les photos
    @GetMapping("/all")
    public ResponseEntity<List<Photo>> getAllPhotos() throws Exception {
        List<Photo> photos = photoRepository.findAll();

        photos.forEach((photo) ->
        {
            try {
                Files.readAllBytes(Paths.get(context.getRealPath("/Images") + photo.getFileName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

}
