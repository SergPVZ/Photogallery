package ru.top_academy.photogallery.mapper;

import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.entity.Photo;

@Component
public class PhotoMapper {

    public PhotoResponseDTO mapToPhotoResponseDTO(Photo photo) {

        return new PhotoResponseDTO(photo.getId(), photo.getName(),
                photo.getGenrePictures(), photo.getUploadDate(), photo.getUpdateAt());

    }

}
