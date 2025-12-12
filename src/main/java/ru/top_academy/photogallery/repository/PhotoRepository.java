package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.Photo;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {

    // Через JPQL запрос
//    @Query("SELECT p FROM Photo p JOIN p.photographer ph WHERE ph.lastName = :lastName")
//    List<Photo> findByPhotographerLastName(@Param("lastName") String lastName);

    // Или если нужно по ID фотографа
    @Query("SELECT p FROM Photo p WHERE p.photographer.id = :photographerId")
    List<Photo> findByPhotographerId(@Param("photographerId") UUID photographerId);

}
