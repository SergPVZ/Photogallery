package ru.top_academy.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO с основной информацией для работы с фотографиями")

public class PhotoResponseDTO {

    @Schema(description = "Идентификационный номер фотографии")
    private UUID id;

    @Schema(description = "Название фотографии")
    private String name;

    @Schema(description = "Жанр")
    private String genrePictures;

    @Schema(description = "Фамилия фотографа")
    private String photographerLastName;

    @Schema(description = "Дата и время загрузки на сайт")
    private LocalDateTime uploadDate;

    @Schema(description="Дата и время обновления")
    private LocalDateTime updateAt;

}
