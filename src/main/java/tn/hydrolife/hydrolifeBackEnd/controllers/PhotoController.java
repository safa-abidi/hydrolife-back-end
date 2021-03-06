package tn.hydrolife.hydrolifeBackEnd.controllers;

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
    public ResponseEntity<Photo> addPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("photo") String photo) throws Exception {
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

        //l'ajouter ?? ce centre
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
    //NEWWWW trouver une photo par son id
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable("id") Long id){
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("photo was not found"));
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    //collecter les photos d'un centre
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Photo>> getPhotosByIdCentre(@PathVariable("id") Long id) throws Exception {
        centreRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("centre with id " + id + " was not found"));
        List<Photo> photos = photoRepository.findByIdCentre(id);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    //collecter tous les photos
    @GetMapping("/all")
    public ResponseEntity<List<Photo>> getAllPhotos() throws Exception {
        List<Photo> photos = photoRepository.findAll();
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    //****only for the pic itself****
    @GetMapping("/find/{id}")
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Photo photo = photoRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+photo.getFileName()));
    }

    @GetMapping("/get/{id}/bycentre/{idCentre}")
    public byte[] getPhotoByIdCentre(@PathVariable("id") Long id, @PathVariable("idCentre") Long idCentre) throws IOException {
        centreRepository.findById(idCentre)
                .orElseThrow(() -> new HydroLifeException("centre with id " + idCentre + " was not found"));

        Photo photo = photoRepository.findById(id).get();

        if(photo.getIdCentre() == idCentre){
            return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+photo.getFileName()));
        }else{
            return null;
        }
    }

}
