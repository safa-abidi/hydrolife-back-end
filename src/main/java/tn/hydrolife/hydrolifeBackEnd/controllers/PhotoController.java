package tn.hydrolife.hydrolifeBackEnd.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Photo;
import tn.hydrolife.hydrolifeBackEnd.repositories.PhotoRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.PhotoService;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    CentreService centreService;

    @PostMapping("/add")
    public BodyBuilder addImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("photo") Photo photo) throws IOException {


        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        //get current logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        //get his id
        Long currentCentreId = currentCentre.get().getId();

        Photo img = new Photo(
                photo.getId_photo(),
                photo.getTitre_photo(),
                photo.getDescription(),
                currentCentreId,
                file.getOriginalFilename(),
                file.getContentType(),
                photoService.compressZLib(file.getBytes()));

        //l'ajouter à ce centre
        currentCentre.get().getPhotos().add(img);

        photoRepository.save(img);
        return ResponseEntity.status(HttpStatus.OK);
    }

    //getting images
//    @GetMapping(path = { "/get/{imageName}" })
//    public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
//
//        final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
//        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
//                decompressZLib(retrievedImage.get().getPicByte()));
//        return img;
//    }

    //collecter les photo d'un même centre par id
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Photo>> getPhotosByIdCentre(@PathVariable("id") Long id){
        //get current logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        //get his id
        Long currentCentreId = currentCentre.get().getId();

        List<Photo> photos = photoRepository.findByIdCentre(id);
        photos.forEach((photo) -> photo = new Photo(
                photo.getId_photo(),
                photo.getTitre_photo(),
                photo.getDescription(),
                currentCentreId,
                photo.getName(),
                photo.getType(),
                photoService.decompressZLib(photo.getPicByte())));

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    //collecter tous les photos
    @GetMapping("/all")
    public ResponseEntity<List<Photo>> getPhotos(){

        List<Photo> photos = photoRepository.findAll();
        photos.forEach((photo) -> photo = new Photo(
                photo.getId_photo(),
                photo.getTitre_photo(),
                photo.getDescription(),
                photo.getIdCentre(),
                photo.getName(),
                photo.getType(),
                photoService.decompressZLib(photo.getPicByte())));

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }


}
