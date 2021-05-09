package tn.hydrolife.hydrolifeBackEnd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;
import tn.hydrolife.hydrolifeBackEnd.repositories.ServicesRepository;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.services.ServicesService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;
    private final ServletContext context;
    private final CentreService centreService;
    private final ServicesRepository servicesRepository;

    //constructor
    public ServicesController(ServicesService servicesService, ServletContext context, CentreService centreService, ServicesRepository servicesRepository) {
        this.servicesService = servicesService;
        this.context = context;
        this.centreService = centreService;
        this.servicesRepository = servicesRepository;
    }

    //trouver tous les services
    @GetMapping("/all")
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = servicesService.findAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    //trouver service par son id
    @GetMapping("/find/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable("id") Long id) {
        Services service = servicesService.findService(id);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    //ajouter un service
    @PostMapping("/add")
    public ResponseEntity<Services> addService(
            @RequestParam("file") MultipartFile file,
            @RequestParam("service") String service) throws Exception{

        Services serv = new ObjectMapper().readValue(service, Services.class);

        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("webapp/Images/")).mkdir();
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try{
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        serv.setFileName(newFileName);

        //get current logged centre
        Optional<Centre> currentCentre = centreService.getCurrentCentre();

        serv.setIdCentre(currentCentre.get().getId());

        currentCentre.get().getServices().add(serv);

        Services saveService = servicesRepository.save(serv);
        if (saveService != null) {
            return new ResponseEntity<>(saveService, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //modifier un service
    @PutMapping("/update")
    public ResponseEntity<Services> updateService(@RequestBody Services service) {
        Services updateService = servicesService.updateService(service);
        return new ResponseEntity<>(updateService, HttpStatus.OK);
    }

    //supprimer un service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) {
        servicesService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //collecter les services d'un même centre par id
    @GetMapping("/findbycentre/{id}")
    public ResponseEntity<List<Services>> getServicesByIdCentre(@PathVariable("id") Long id) {
        List<Services> services = servicesService.findServicesByCentre(id);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    //for pic itself
    @GetMapping("/get/{id}/bycentre/{idCentre}")
    public byte[] getPhotoByIdService(@PathVariable("id") Long id, @PathVariable("idCentre") Long idCentre) throws IOException{
        Services service = servicesService.findService(id);
        if(service.getIdCentre() == idCentre){
            return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + service.getFileName()));
        }else{
            return null;
        }

    }

    //rechercher les services par libelle
    @GetMapping("/rechercher")
        public ResponseEntity<List<Services>> getByLibelle(@RequestParam(required = false, defaultValue = "") String mot, @RequestParam(required = false, defaultValue = "") String mot2){
            List<Services> services = servicesService.findServicesByLibelleOrDescription(mot, mot2);
            return new ResponseEntity<>(services, HttpStatus.OK);
        }
}
