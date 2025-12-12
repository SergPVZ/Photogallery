package ru.top_academy.photogallery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class Photo {

    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String genrePictures;

//    @Column
//    private String photographerLastName;

//    @Column
//    private String fileURL;

    @Column
    private LocalDateTime uploadDate;

    @Column
    @LastModifiedDate
    private LocalDateTime updateAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer photographerName;

}
