package ru.top_academy.photogallery.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.PhotoFileResponseDTO;
import ru.top_academy.photogallery.entity.PhotoFile;

@Component
public class PhotoFileMapper {

    @Value("${server.url:http://localhost:8000}")
    private String serverUrl;

    public PhotoFileResponseDTO toDto(PhotoFile photoFile) {

        if (photoFile == null) {
            return null;
        }

        PhotoFileResponseDTO dto = new PhotoFileResponseDTO();
        dto.setId(photoFile.getId());
        dto.setOriginalFilename(photoFile.getOriginalFilename());
        dto.setFilename(photoFile.getFilename());
        dto.setContentType(photoFile.getContentType());
        dto.setSize(photoFile.getSize());
        dto.setDescription(photoFile.getDescription());
        if (photoFile.getPhotographer() != null) {
            dto.setPhotographerId(photoFile.getPhotographer().getId());
            dto.setPhotographerName(photoFile.getPhotographer().getPhotographerFirstName() + " " +
                                    photoFile.getPhotographer().getPhotographerLastName());
        }
        dto.setUploadDate(photoFile.getUploadDate());
        dto.setTags(photoFile.getTags());
        dto.setFileUrl(serverUrl + "/api/photos/files/" + photoFile.getId() + "/content");
        dto.setActive(photoFile.isActive());

        return dto;

    }

}
