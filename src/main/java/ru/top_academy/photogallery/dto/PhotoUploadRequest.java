package ru.top_academy.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@Schema(description = "Запрос на загрузку фото")
public class PhotoUploadRequest {

    @Schema(description = "Файл изображения")
    private MultipartFile file;

    @Schema(description = "ID фотографа")
    private UUID photographer;

    @Schema(description = "Описание фото")
    private String description;

    @Schema(description = "Теги")
    private String tags;

    @Schema(description = "Имя")
    private String customFileName;

}
