package ru.top_academy.photogallery.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.top_academy.photogallery.dto.ViewerResponseDTO;
import ru.top_academy.photogallery.entity.Viewer;
import ru.top_academy.photogallery.mapper.ViewerMapper;
import ru.top_academy.photogallery.repository.ViewerRepository;
import ru.top_academy.photogallery.request.ViewerRequest;

import java.util.UUID;


@Service
@Transactional(readOnly = true)  //  попробовать импортировать "Transaction-api"
@Validated
public class ViewerService {

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private ViewerMapper viewerMapper;

    @Transactional(rollbackFor = Exception.class)
    public ViewerResponseDTO createViewer( Viewer request) {

        Viewer viewer = new Viewer(UUID.randomUUID(), request.getName(), request.getEmail(), request.getCity(),
                        request.getRegistrationDate(), request.isActive(), request.getUpdatedAt());

//        viewer = repository.save(viewer);
        viewerRepository.saveAndFlush(viewer);

        return viewerMapper.mapToViewerResponseDTO(viewer);

    }

    @Transactional(rollbackFor = Exception.class)
    public ViewerResponseDTO updateViewer(UUID id, @Valid ViewerRequest request) {

        Viewer viewer = viewerRepository.findById(id).orElse(null);

        viewer.setName(request.getName());
        viewer.setEmail(request.getEmail());
        viewer.setCity(request.getCity());

        viewerRepository.saveAndFlush(viewer);

        return viewerMapper.mapToViewerResponseDTO(viewer);

    }

    public ViewerResponseDTO getViewerById(UUID id) {

        Viewer viewer = viewerRepository.findById(id).orElse(null);

        return viewerMapper.mapToViewerResponseDTO(viewer);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteViewer(UUID id) {
        viewerRepository.deleteById(id);
    }

}
