package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Services;
import tn.hydrolife.hydrolifeBackEnd.services.ServicesService;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    //constructor
    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    //trouver tous les services
    @GetMapping("/all")
    public ResponseEntity<List<Services>> getAllServices(){
        List<Services> services = servicesService.findAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    //trouver service avec id
    @GetMapping("/find/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable("id") Long id){
        Services service = servicesService.findService(id);
        return new ResponseEntity<Services>(service, HttpStatus.OK);
    }
    //ajouter un service
    @PostMapping("/add")
    public ResponseEntity<Services> addService(@RequestBody Services service){
        Services newService = servicesService.addService(service);
        return new ResponseEntity<>(newService, HttpStatus.CREATED);
    }

    //modifier un service
    @PutMapping("/update")
    public ResponseEntity<Services> updateService(@RequestBody Services service){
        Services updateService = servicesService.updateService(service);
        return new ResponseEntity<>(updateService, HttpStatus.OK);
    }
    //supprimer un service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id){
        servicesService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
