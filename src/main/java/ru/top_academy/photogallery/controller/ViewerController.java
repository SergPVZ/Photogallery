package ru.top_academy.photogallery.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top_academy.photogallery.dto.ViewerResponseDTO;
import ru.top_academy.photogallery.entity.Viewer;
import ru.top_academy.photogallery.request.ViewerRequest;
import ru.top_academy.photogallery.service.ViewerService;

import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class ViewerController {

    @Autowired
    private ViewerService service;

    @PostMapping("/добавления нового пользователя")
    public ResponseEntity<ViewerResponseDTO> createViewer(@Valid @RequestBody Viewer viewer) {

        ViewerResponseDTO viewer1 = service.createViewer(viewer);

        return ResponseEntity.ok(viewer1);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewerResponseDTO> updateViewer(@PathVariable UUID id,
                                                          @Valid @RequestBody ViewerRequest request) {

        ViewerResponseDTO viewer = service.updateViewer(id, request);

        return ResponseEntity.ok(viewer);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewerResponseDTO> getViewerById(@PathVariable UUID id) {

        ViewerResponseDTO viewerById = service.getViewerById(id);

        return ResponseEntity.ok(viewerById);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ViewerResponseDTO> deleteViewerById(@PathVariable UUID id) {

        service.deleteViewer(id);

        return ResponseEntity.noContent().build();

    }

}
