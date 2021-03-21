package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.entities.Client;
import tn.hydrolife.hydrolifeBackEnd.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    //trouver tous les clients
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients(){
        List<Client> clients = clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    //trouver un client par son id
    @GetMapping("/find/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id){
        Client client = clientService.findClient(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //trouver un client avec son email
    @GetMapping("/get/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable("email") String email){
        Client client = clientService.findClientByEmail(email);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //inscrire un client
    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        //password encoding
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        Client newClient = clientService.addClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    //modifier les informations d'un client
    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client){
        //password encoding
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        Client updateClient = clientService.updateClient(client);
        return new ResponseEntity<>(updateClient, HttpStatus.OK);
    }

    //supprimer un client
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
