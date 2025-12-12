package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.PhotoFile;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoFileRepository extends JpaRepository<PhotoFile, UUID> {

    List<PhotoFile> findByPhotographerId(UUID photographerId);

    List<PhotoFile> findByTagsContains(String tag);

    List<PhotoFile> findByIsActiveTrue();

    List<PhotoFile> findByIsActive(boolean isActive);

}
