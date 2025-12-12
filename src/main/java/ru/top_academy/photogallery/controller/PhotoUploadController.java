package ru.top_academy.photogallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.top_academy.photogallery.dto.PhotoFileResponseDTO;
import ru.top_academy.photogallery.service.PhotoFileService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/simple-upload")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoUploadController {

    @Autowired
    private PhotoFileService photoFileService;

    @PostMapping
    public ResponseEntity<PhotoFileResponseDTO> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("photographerId") UUID photographerId,
            @RequestParam(value = "filename", required = false) String customFilename,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags) throws IOException {

        PhotoFileResponseDTO pesponce = photoFileService.uploadWithCustomName(file, photographerId,// uploadWithCustomName
                customFilename,description,tags);

        return ResponseEntity.ok(pesponce);

    }
}
