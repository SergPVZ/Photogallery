package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.PhotoFile;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoFileRepository extends JpaRepository<PhotoFile, UUID> {

    List<PhotoFile> findByPhotographerId(UUID photographerId);

    @Query("SELECT p FROM PhotoFile p WHERE p.isActive = true")
    List<PhotoFile> findByIsActiveTrue();
    
}
