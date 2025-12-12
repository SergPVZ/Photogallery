package ru.top_academy.photogallery.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.top_academy.photogallery.dto.PhotographerResponseDto;
import ru.top_academy.photogallery.entity.Photographer;
import ru.top_academy.photogallery.mapper.PhotographerMapper;
import ru.top_academy.photogallery.repository.PhotographerRepository;
import ru.top_academy.photogallery.request.PhotographerRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)  //  попробовать импортировать "Transaction-api"
@Validated
public class PhotographerService {

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private PhotographerMapper photographerMapper;

    @Transactional(rollbackFor = Exception.class)
    public PhotographerResponseDto createPhotographer(PhotographerRequest request) {
//
        Photographer photographer = new Photographer(UUID.randomUUID(),
                request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPhone(),
                request.getAddress(), request.getCity(),
                request.getRegistrationDate(), request.isActive(), request.getUpdatedAt());

        photographerRepository.saveAndFlush(photographer);

        return photographerMapper.mapToPhotographerResponseDto(photographer);

//        return new PhotographerResponseDto(UUID.randomUUID(),
//                photographer.getFirstName(), photographer.getLastName(),
//                photographer.getEmail(), photographer.getPhone(),
//                photographer.getAddress(), photographer.getCity(),
//                photographer.getRegistrationDate(), photographer.isActive(),
//                photographer.getUpdatedAt());

    }

        @Transactional(rollbackFor = Exception.class)
    public PhotographerResponseDto updatePhotographer(UUID id, @Valid PhotographerRequest request) {

        Photographer photographer = photographerRepository.findById(id).orElse(null);

        photographer.setPhotographerFirstName(request.getFirstName());
        photographer.setPhotographerLastName(request.getLastName());
        photographer.setEmail(request.getEmail());
        photographer.setPhone(request.getPhone());
        photographer.setAddress(request.getAddress());
        photographer.setCity(request.getCity());

        photographerRepository.saveAndFlush(photographer);

        return photographerMapper.mapToPhotographerResponseDto(photographer);

    }

    public PhotographerResponseDto getPhotographer(UUID id) {

        Photographer photographer = photographerRepository.findById(id).orElse(null);

        return photographerMapper.mapToPhotographerResponseDto(photographer);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePhotographer(UUID id) {
        photographerRepository.deleteById(id);
    }

    public List<PhotographerResponseDto> getPhotoByName(String firstName, String lastName) {

        List<Photographer> byFirstNameAndLastName = photographerRepository.findByFirstNameAndLastName(firstName, lastName);

        return byFirstNameAndLastName.stream()
                .map(photographer -> photographerMapper.mapToPhotographerResponseDto(photographer))
                .collect(Collectors.toList());

    }

}
