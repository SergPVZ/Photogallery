package ru.top_academy.photogallery.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.dto.PhotographerResponseDto;
import ru.top_academy.photogallery.entity.Photo;
import ru.top_academy.photogallery.service.PhotoService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/добавить новую фотографию")
    public ResponseEntity<PhotoResponseDTO> createPhoto(@Valid @RequestBody Photo photo, @RequestParam UUID photographerId) {

        PhotoResponseDTO photoResponseDTO = photoService.downloadNewPhoto(photo, photographerId);

        return ResponseEntity.ok(photoResponseDTO);

    }

    @GetMapping("/{id} get photo by id")
    public ResponseEntity<PhotoResponseDTO> getPhotoById(@PathVariable UUID id) {

        PhotoResponseDTO photo = photoService.getPhoto(id);

        return ResponseEntity.ok(photo);

    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<PhotoResponseDTO>> getPhotoByPhotographer(@PathVariable UUID photographerId) {

        List<PhotoResponseDTO> photosByPhotographer = photoService.getPhotosByPhotographer(photographerId);

        return ResponseEntity.ok(photosByPhotographer);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PhotographerResponseDto> deletePhoto(@PathVariable UUID id) {

        photoService.deletePhoto(id);

        return ResponseEntity.noContent().build();

    }

}
