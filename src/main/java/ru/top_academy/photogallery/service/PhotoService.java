package ru.top_academy.photogallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.top_academy.photogallery.dto.PhotoResponseDTO;
import ru.top_academy.photogallery.entity.Photo;
import ru.top_academy.photogallery.mapper.PhotoMapper;
import ru.top_academy.photogallery.repository.PhotoRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Validated
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoMapper photoMapper;

    @Transactional(rollbackFor = Exception.class)
    public PhotoResponseDTO downloadNewPhoto(Photo request) {

        Photo photo = new Photo(UUID.randomUUID(), request.getName(), request.getGenrePictures(),
                request.getUploadDate(), request.getUpdateAt());

        photoRepository.saveAndFlush(photo);

        return photoMapper.mapToPhotoResponseDTO(photo);

    }

    public PhotoResponseDTO getPhoto(UUID id) {

        Photo photo = photoRepository.findById(id).orElse(null);

        return photoMapper.mapToPhotoResponseDTO(photo);

    }

}
