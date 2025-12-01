package ru.top_academy.photogallery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO с основной информацией для работы с пользователями")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewerResponseDTO {

    @Schema(description = "Идентификатор пользователя")
    private UUID id;

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Электронная почта пользователя")
    private String email;

    @Schema(description = "Часовой пояс")
    private String city;

    @Schema(description = "Дата регистрации")
    private LocalDateTime registrationDate;

    @Schema(description = "Активность пользователя на сайте")
    private boolean isActive;

    @Schema(description = " /// ")
//    @LastModifiedDate
    private LocalDateTime updatedAt;

}
