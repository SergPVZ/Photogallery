package ru.top_academy.photogallery.mapper;

import org.springframework.stereotype.Component;
import ru.top_academy.photogallery.dto.PhotographerResponseDto;
import ru.top_academy.photogallery.entity.Photographer;

@Component
public class PhotographerMapper {

    public PhotographerResponseDto mapToPhotographerResponseDto(Photographer photographer) {

        return new PhotographerResponseDto(photographer.getId(),
                photographer.getFirstName(), photographer.getLastName(),
                photographer.getEmail(), photographer.getPhone(),
                photographer.getAddress(), photographer.getCity(),
                photographer.getRegistrationDate(), photographer.isActive(), photographer.getUpdatedAt());

    }

}
