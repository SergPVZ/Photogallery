package ru.top_academy.photogallery.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top_academy.photogallery.dto.PhotographerResponseDto;
import ru.top_academy.photogallery.request.PhotographerRequest;
import ru.top_academy.photogallery.service.PhotographerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/photographer")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotographerController {

    @Autowired
    private PhotographerService photographerService;

    @PostMapping("/добавить нового фотографа")
    public ResponseEntity<PhotographerResponseDto> createPhotographer(@Valid @RequestBody PhotographerRequest request) {

        PhotographerResponseDto photographer = photographerService.createPhotographer(request);

        return ResponseEntity.ok(photographer);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotographerResponseDto> updatePhotographerById(@PathVariable UUID id,
                                                                      @Valid @RequestBody PhotographerRequest request) {

        PhotographerResponseDto photographer = photographerService.updatePhotographer(id, request);

        return ResponseEntity.ok(photographer);

    }

    @GetMapping("/{id} получить по id")
    public ResponseEntity<PhotographerResponseDto> getPhotographerById(@PathVariable UUID id) {

        PhotographerResponseDto photographer = photographerService.getPhotographer(id);

        return ResponseEntity.ok(photographer);

    }

    @GetMapping("/получить фотографа по имени")
    public ResponseEntity<List<PhotographerResponseDto>> getPhotoByName(@RequestParam String firstName,
                                                                        @RequestParam String lastName) {

        List<PhotographerResponseDto> photographer = photographerService.getPhotoByName(firstName, lastName);

        return ResponseEntity.ok(photographer);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PhotographerResponseDto> deletePhotographer(@PathVariable UUID id) {

        photographerService.deletePhotographer(id);

        return ResponseEntity.noContent().build();

    }

}
