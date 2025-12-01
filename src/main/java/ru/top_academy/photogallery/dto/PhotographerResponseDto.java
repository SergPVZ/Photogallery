package ru.top_academy.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO с основной информацией для работы с фотографами")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerResponseDto {

    @Schema(description ="Идентификатор фотографа")
    private UUID id;

    @Schema(description ="Имя фотографа")
    private String firstName;

    @Schema(description ="Фамилия фотографа")
    private String lastName;

    @Schema(description ="Электронная почта фотографа")
    private String email;

    @Schema(description ="Номер телефона")
    private String phone;

    @Schema(description ="Адрес фотографа")
    private String address;

    @Schema(description ="Город регистрации фотографа")
    private String city;

    @Schema(description ="Дата регистрации на сайте")
    private LocalDateTime registrationDate;
//
    @Schema(description = "Наличие активности фотографа")
    private boolean isActive;

    @Schema(description = " /// ")
//    @LastModifiedDate
    private LocalDateTime updatedAt;

//    public PhotographerResponseDto(UUID id, String firstName, String lastName, String email, String phone, String address, String city, LocalDateTime registrationDate, boolean active, LocalDateTime updatedAt) {
//    }
}
