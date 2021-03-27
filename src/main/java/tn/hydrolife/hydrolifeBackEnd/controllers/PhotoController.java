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
import tn.hydrolife.hydrolifeBackEnd.repositories.PhotoRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.PhotoService;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
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

    @PostMapping("/add")
    public ResponseEntity<Photo> addPhoto(@RequestParam("file")MultipartFile file, @RequestParam("photo") String photo) throws JsonParseException, JsonMappingException, Exception {

        Photo thePhoto = new ObjectMapper().readValue(photo, Photo.class);
        boolean isExist = new File(context.getRealPath("/Images/")).exists();

        if(!isExist){
            new File(context.getRealPath("/Images/")).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(fileName)+"."+FilenameUtils.getExtension(fileName);
        File serverFile = new File(context.getRealPath("/Images/"+File.separator+newFileName));
        try{
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }

        thePhoto.setFileName(newFileName);

        //get current logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();
        //get his id
        Long currentCentreId = currentCentre.get().getId();
        //set idCentre in the photo
        thePhoto.setIdCentre(currentCentreId);

        //l'ajouter à ce centre
        currentCentre.get().getPhotos().add(thePhoto);

        Photo ph = photoRepository.save(thePhoto);
        if(ph != null){
            return new ResponseEntity<>(thePhoto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
