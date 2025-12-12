package ru.top_academy.photogallery.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private ResourceLocations resourceLocations = new ResourceLocations();

    @Getter
    @Setter
    public static class ResourceLocations {

        private String images;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

