package ru.top_academy.photogallery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "photo_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String filePath;

    @Column(name = "relative_patch")
    private String relativePath;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @Column
    private String tags;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
