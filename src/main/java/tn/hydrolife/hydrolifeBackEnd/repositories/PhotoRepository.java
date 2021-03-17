package tn.hydrolife.hydrolifeBackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.hydrolife.hydrolifeBackEnd.entities.Photo;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
