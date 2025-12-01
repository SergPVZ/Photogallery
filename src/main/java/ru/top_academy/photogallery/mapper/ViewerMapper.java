package ru.top_academy.photogallery.mapper;

import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.ViewerResponseDTO;
import ru.top_academy.photogallery.entity.Viewer;

@Component
public class ViewerMapper {

    public ViewerResponseDTO mapToViewerResponseDTO(Viewer viewer) {
        return new ViewerResponseDTO(viewer.getId(), viewer.getName(), viewer.getEmail(), viewer.getCity(),
                viewer.getRegistrationDate(), viewer.isActive(), viewer.getUpdatedAt());
    }

}
