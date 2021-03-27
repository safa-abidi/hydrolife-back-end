package tn.hydrolife.hydrolifeBackEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetails;
import tn.hydrolife.hydrolifeBackEnd.entities.Client;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //ajouter un client
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    //trouver tous les clients
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    //modifier
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    //supprimer par id
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    //trouver le client par son id
    public Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new HydroLifeException("Client with id " + id + " was not found"));
    }

    //trouver un client par son email
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new HydroLifeException("Client with email " + email + " was not found"));
    }

    //GET LOGGED CLIENT DETAILS
    @Transactional(readOnly = true)
    public Optional<Client> getCurrentClient() {
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return clientRepository.findByEmail(principal.getUsername());
    }
}
