package ru.top_academy.photogallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.top_academy.photogallery.entity.Viewer;

import java.util.UUID;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, UUID>{

}
