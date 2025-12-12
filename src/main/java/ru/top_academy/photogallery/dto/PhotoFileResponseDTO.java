package ru.top_academy.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для информации о загруженном файле")
public class PhotoFileResponseDTO {

    @Schema(description = "ID файла")
    private UUID id;

    @Schema(description = "Оригинальное имя файла")
    private String originalFilename;

    @Schema(description = "Имя файла в системе")
    private String filename;

    @Schema(description = "Тип содержимого")
    private String contentType;

    @Schema(description = "Размер файла в байтах")
    private Long size;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "ID фотографа")
    private UUID photographerId;

    // изменить на photographerLastName
    @Schema(description = "Имя фотографа")
    private String photographerName;

    @Schema(description = "Дата загрузки")
    private LocalDateTime uploadDate;

    @Schema(description = "Теги")
    private String tags;

    @Schema(description = "URL для доступа к файлу")
    private String fileUrl;

    @Schema(description = "Активен ли файл")
    private boolean active;

}
