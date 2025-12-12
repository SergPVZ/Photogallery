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

List<Photo> findByPhotographerId(UUID photographerId);
}
