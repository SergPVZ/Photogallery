package ru.top_academy.photogallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.entity.Photo;
import ru.top_academy.photogallery.entity.Photographer;
import ru.top_academy.photogallery.mapper.PhotoMapper;
import ru.top_academy.photogallery.repository.PhotoRepository;
import ru.top_academy.photogallery.repository.PhotographerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional(readOnly = true)
@Validated
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Transactional(rollbackFor = Exception.class)
    public PhotoResponseDTO downloadNewPhoto(Photo photo) {
        Photographer photographer = photographerRepository.findByPhotographerLastName(findByPhotographerLastName));

        Photo photo = new Photo(UUID.randomUUID(), photo.getName(), photo.getGenrePictures(),
                photo.getPhotographerName(),
                photo.getUploadDate(), photo.getUpdateAt());

        photoRepository.saveAndFlush(photo);

        return photoMapper.mapToPhotoResponseDTO(photo);

    }

    public PhotoResponseDTO getPhoto(UUID id) {

        Photo photo = photoRepository.findById(id).orElse(null);

        return photoMapper.mapToPhotoResponseDTO(photo);

    }

    public List<PhotoResponseDTO> getPhotosByPhotographer(String lastName) {
        return photoRepository.findByPhotographerLastName(lastName)
                .stream()
                .map(photoMapper::mapToPhotoResponseDTO)
                .collect(Collectors.toList());
    }

//    public List<PhotoResponseDTO> getAllPhotos() {
//        return photoRepository.findByIsActiveTrue()
//                .stream()
//                .map(photoMapper::toDto)
//                .collect(Collectors.toList());
//    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePhoto(UUID id) {
        photoRepository.deleteById(id);
    }

}
