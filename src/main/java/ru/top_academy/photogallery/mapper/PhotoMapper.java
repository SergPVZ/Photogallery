package ru.top_academy.photogallery.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.entity.Photo;
import ru.top_academy.photogallery.entity.Photographer;

@Component
public class PhotoMapper {

//    @Value("${server.url:http://localhost:8080}")

    public PhotoResponseDTO mapToPhotoResponseDTO(Photo photo) {

        if (photo == null) {
            return null;
        }

        PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();

        photoResponseDTO.setId(photo.getId());
        photoResponseDTO.setName(photo.getName());
        photoResponseDTO.setGenrePictures(photo.getGenrePictures());
        photoResponseDTO.setUploadDate(photo.getUploadDate());
        photoResponseDTO.setUpdateAt(photo.getUpdateAt());
        if (photo.getPhotographer() != null) {
            Photographer photographer = photo.getPhotographer();
        }

        return photoResponseDTO;
    }

}
