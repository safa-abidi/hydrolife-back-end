package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Service;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
}
