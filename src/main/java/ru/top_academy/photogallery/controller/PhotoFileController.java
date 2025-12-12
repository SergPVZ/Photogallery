package ru.top_academy.photogallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.top_academy.photogallery.dto.PhotoFileResponseDTO;
import ru.top_academy.photogallery.service.PhotoFileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoFileController {

    @Autowired
    private PhotoFileService photoFileService;

    @PostMapping("/upload")
    public ResponseEntity<PhotoFileResponseDTO> uploadPhotoFile(
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("photographerId") UUID photographerId,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "tags", required = false) String tags) {  // ещё добавить

        try {
            PhotoFileResponseDTO response = photoFileService.uploadPhoto(file, photographerId, description, tags);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoFileResponseDTO> getPhotoById(@PathVariable UUID id) {

        PhotoFileResponseDTO photoById = photoFileService.getPhotoById(id);

        return ResponseEntity.ok(photoById);

    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<PhotoFileResponseDTO>> getPhotoByPhotographer(@PathVariable UUID photographerId) {

        List<PhotoFileResponseDTO> photosByPhotographer = photoFileService.getPhotosByPhotographer(photographerId);

        return ResponseEntity.ok(photosByPhotographer);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PhotoFileResponseDTO>> getAllPhotos() {

        List<PhotoFileResponseDTO> photos = photoFileService.getAllPhotos();

        return ResponseEntity.ok(photos);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PhotoFileResponseDTO> deletePhoto(@PathVariable UUID id) {  // по возможности переделать

        try {
            photoFileService.deletePhoto(id);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
