package ru.top_academy.photogallery.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="DTO запроса создания фотографий")

public class PhotoRequest {

    @Schema(description="Идентификационный номер фотографии")
    private UUID id;

    @Schema(description="Название фотографии")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description="Жанр")
    @JsonProperty("genre_pictures")
    private String genrePictures;

    @Schema(description="Дата и время загрузки на сайт")
    @JsonProperty("upload_date")
    @NotNull
    private LocalDateTime uploadDate;

    @Schema(description="Дата и время обновления")
    @JsonProperty("updateAt")
    private LocalDateTime updateAt;

}
