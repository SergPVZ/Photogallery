package ru.top_academy.photogallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.top_academy.photogallery.dto.PhotoFileResponseDTO;
import ru.top_academy.photogallery.entity.PhotoFile;
import ru.top_academy.photogallery.entity.Photographer;
import ru.top_academy.photogallery.mapper.PhotoFileMapper;
import ru.top_academy.photogallery.repository.PhotoFileRepository;
import ru.top_academy.photogallery.repository.PhotographerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoFileService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Autowired
    private PhotoFileRepository photoFileRepository;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private PhotoFileMapper photoFileMapper;

    public PhotoFileResponseDTO uploadPhoto(MultipartFile file, UUID photographerId,
                                            String description, String tags) throws IOException {

        // Проверяем существование фотографа
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new RuntimeException("Фотограф не найден"));

        // Создаем директорию если не существует
        Path uploadPath = Paths.get(uploadDir);

        PhotoFile photoFile = null;  // убрать нафиг!!!!!
        photoFile.setActive(true);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Создаём оригинальное имя файла
        String originalPhotoFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        if (originalPhotoFileName.contains(".")) {
            fileExtension = originalPhotoFileName.substring(originalPhotoFileName.lastIndexOf("."));
        }

        String photoName = UUID.randomUUID().toString() + fileExtension;

        // Сохраняем файл
        Path filePath = uploadPath.resolve(photoName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Сохраняем информацию в БД
        photoFile = new PhotoFile();
        photoFile.setId(UUID.randomUUID());
        photoFile.setOriginalFilename(originalPhotoFileName);
        photoFile.setFilename(photoName);
        photoFile.setContentType(file.getContentType());
        photoFile.setSize(file.getSize());
        photoFile.setFilePath(filePath.toString());
        photoFile.setDescription(description);
        photoFile.setPhotographer(photographer);
        photoFile.setUploadDate(LocalDateTime.now());
        photoFile.setTags(tags);
        photoFile.setActive(true);

        photoFileRepository.save(photoFile);

        return photoFileMapper.toDto(photoFile);

    }

    public List<PhotoFileResponseDTO> getPhotosByPhotographer(UUID photographerId) {
        return photoFileRepository.findByPhotographerId(photographerId)
                .stream()
                .map(photoFileMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PhotoFileResponseDTO> getAllPhotos() {
        return photoFileRepository.findByIsActiveTrue()
                .stream()
                .map(photoFileMapper::toDto)
                .collect(Collectors.toList());
    }

    public PhotoFileResponseDTO getPhotoById(UUID id) {

        PhotoFile photoFile = photoFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фото не найдено"));

        return photoFileMapper.toDto(photoFile);

    }

    @Transactional
    public void deletePhoto(UUID id) throws IOException {

        PhotoFile photoFile = photoFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фото не найдено"));

        // Удаляем физический файл
        Files.deleteIfExists(Paths.get(photoFile.getFilePath()));

        // Удаляем запись из БД
        photoFileRepository.delete(photoFile);

    }

    public byte[] getPhotoContent(UUID id) throws IOException {

        PhotoFile photoFile = photoFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фото не найдено"));

        return Files.readAllBytes(Paths.get(photoFile.getFilePath()));

    }

    public List<PhotoFileResponseDTO> getAllActivePhotos() {
        return photoFileRepository.findByIsActiveTrue()  // ✅ Теперь работает
                .stream()
                .map(photoFileMapper::toDto)
                .collect(Collectors.toList());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePhoto(@PathVariable UUID id) {
//        try {
//            photoFileService.deletePhoto(id);
//            return ResponseEntity.noContent().build();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}
