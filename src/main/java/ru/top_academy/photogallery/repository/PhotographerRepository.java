package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.Photographer;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, UUID> {

    @Query("SELECT p FROM photographer p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    List<Photographer> findByFirstNameAndLastName(@Param("firstName") String firstName,
                                                  @Param("lastName") String lastName);

}
