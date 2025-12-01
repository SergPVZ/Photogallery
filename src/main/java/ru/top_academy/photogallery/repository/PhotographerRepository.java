package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.Photographer;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, UUID> {

    List<Photographer> findByFirstNameAndLastName(String firstName, String lastName);

}
