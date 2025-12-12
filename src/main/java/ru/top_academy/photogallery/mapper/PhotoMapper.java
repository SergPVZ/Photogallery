package ru.top_academy.photogallery.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.entity.Photo;
import ru.top_academy.photogallery.entity.Photographer;

@Component
public class PhotoMapper {

    @Value("${server.url:http://localhost:8080}")
    private String serverUrl;

    public String mapPhotographerToString(Photographer photographer) {

        return photographer.getPhotographerFirstName() + " " + photographer.getPhotographerLastName();

    }

    public PhotoResponseDTO mapToPhotoResponseDTO(Photo photo) {

//        if (photo == null) {
//            return null;
//        }
//
//        return new PhotoResponseDTO(photo.getId(), photo.getName(), photo.getGenrePictures(),
//                mapPhotographerToString(Photographer), photo.getFileURL(), photo.getUploadDate(), photo.getUpdateAt());
//
//    }

//    public PhotoResponseDTO toDto(Photo photo) {

        if (photo == null) {
            return null;
        }

        PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();

        photoResponseDTO.setId(photo.getId());
//        photoResponseDTO.setOriginalFilename(photo.getOriginalFilename());
        photoResponseDTO.setName(photo.getName());
        photoResponseDTO.setGenrePictures(photo.getGenrePictures());
//        photoResponseDTO.setSize(photoFile.getSize());
//        photoResponseDTO.setDescription(photoFile.getDescription());

//        if (photo.getPhotographer() != null) {
//            photoResponseDTO.setPhotographerName(
//                    photo.getPhotographer().getPhotographerFirstName() + " " +
//                    photo.getPhotographer().getPhotographerLastName());
////        }
//        photoResponseDTO.setPhotographerLastName(photo.getPhotographerName());
//        photoResponseDTO.setFileUrl(serverUrl + "/api/photos/files/" + photo.getId() + "/content");
        photoResponseDTO.setUploadDate(photo.getUploadDate());
        photoResponseDTO.setUpdateAt(photo.getUpdateAt());
        if (photo.getPhotographerName() != null) {
            Photographer photographer = photo.getPhotographerName();
        }

        return photoResponseDTO;
    }

}
