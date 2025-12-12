package ru.top_academy.photogallery.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "DTO запроса создания пользователей сайта")
public class ViewerRequest {

    @Schema(description = "Идентификатор пользователя")
    private UUID id;

    @Schema(description = "Имя пользователя")
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Schema(description = "Электронная почта пользователя")
    @JsonProperty("email")
    @Email
    private String email;

    @Schema(description = "Часовой пояс")
    @JsonProperty("city")
    @NotBlank
    private String city;

    @Schema(description = "Дата регистрации")
    @JsonProperty("registration_date")
    @NotNull
    private LocalDateTime registrationDate;

    @Schema(description = "Активность пользователя на сайте")
    @JsonProperty("is_active")
    private boolean isActive;

    @Schema(description = "Дата последнего обновления данных")
    @JsonProperty("updateAt")
    private LocalDateTime updatedAt;

}
