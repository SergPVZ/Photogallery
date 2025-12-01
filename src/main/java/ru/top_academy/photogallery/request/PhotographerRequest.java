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

@Schema(description = "DTO запроса создания фотографа")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerRequest {

    @Schema(description = "Идентификатор фотографа")
    private UUID id;

    @Schema(description = "Имя фотографа")
    @JsonProperty("firstName")
    @NotBlank
    private String firstName;

    @Schema(description = "Фамилия фотографа")
    @JsonProperty("lastName")
    @NotBlank
    private String lastName;

    @Schema(description = "Электронная почта фотографа")
    @JsonProperty("email")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Телефон фотографа")
    @JsonProperty("phone")
    @NotBlank
    private String phone;

    @Schema(description = "Адрес фотографа")
    @JsonProperty("address")
    @NotBlank
    private String address;

    @Schema(description = "Часовой пояс фотографа")
    @JsonProperty("city")
    @NotBlank
    private String city;

    @Schema(description = "Дата регистрации на сайте")
    @JsonProperty("registrationDate")
    @NotNull
    private LocalDateTime registrationDate;

    @Schema(description = "Наличие активности фотографа")
    @JsonProperty("isActive")
    private boolean isActive;

    @Schema(description = "Дата последнего обновления данных")
    @JsonProperty("updateAt")
    private LocalDateTime updatedAt;

}

