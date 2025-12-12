package ru.top_academy.photogallery.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new RuntimeException("Фотограф не найден"));

        Path uploadPath = Paths.get(uploadDir);

        PhotoFile photoFile = null;  // убрать нафиг!!!!!
        photoFile.setActive(true);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalPhotoFileName = StringUtils.cleanPath(file.getOriginalFilename());

        String fileExtension = getFileExtension(originalPhotoFileName);

        if (originalPhotoFileName.contains(".")) {
            fileExtension = originalPhotoFileName.substring(originalPhotoFileName.lastIndexOf("."));
        }

        String photoName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(photoName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

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

    public PhotoFileResponseDTO uploadWithCustomName(
            MultipartFile file,
            UUID photographerId,
            String customFilename,
            String description,
            String tags) throws IOException {

        // 1. Проверка входных параметров
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл не может быть пустым");
        }

        if (photographerId == null) {
            throw new IllegalArgumentException("ID фотографа не может быть пустым");
        }

        if (customFilename == null || customFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        // 2. Проверяем существование фотографа
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new RuntimeException("Фотограф с ID " + photographerId + " не найден"));

        // 3. Обработка имени файла
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        // Проверяем безопасность имени файла
        if (originalFilename.contains("..")) {
            throw new IllegalArgumentException("Некорректное имя файла: " + originalFilename);
        }

        // 4. Получаем расширение файла
        String fileExtension = getFileExtension(originalFilename);
        if (fileExtension.isEmpty()) {
            fileExtension = ".jpg"; // Расширение по умолчанию для изображений
        }

        // 5. Проверяем допустимые форматы изображений
        if (!isValidImageExtension(fileExtension)) {
            throw new IllegalArgumentException(
                    "Недопустимый формат файла. Разрешены: JPG, JPEG, PNG, GIF, BMP, WEBP. Получено: " + fileExtension
            );
        }

        // 6. Подготовка имени файла
        String sanitizedCustomName = sanitizeFilename(customFilename);
        String finalFilename = sanitizedCustomName + fileExtension;

        // 7. Получаем путь для сохранения файла
        Path uploadPath = getUploadPath();
        Path datePath = createDateSubdirectory(uploadPath);

        // 8. Убеждаемся, что имя файла уникально
        finalFilename = ensureUniqueFilename(finalFilename, datePath);

        // 9. Сохраняем файл на диск
        Path filePath = datePath.resolve(finalFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 10. Создаем запись в базе данных
        PhotoFile photoFile = new PhotoFile();
        photoFile.setId(UUID.randomUUID());
        photoFile.setOriginalFilename(originalFilename);
        photoFile.setFilename(finalFilename);
        photoFile.setContentType(file.getContentType());
        photoFile.setSize(file.getSize());
        photoFile.setFilePath(filePath.toString());
        photoFile.setRelativePath(getRelativePath(filePath));
        photoFile.setDescription(description);
        photoFile.setPhotographer(photographer);
        photoFile.setUploadDate(LocalDateTime.now());
        photoFile.setTags(tags);
        photoFile.setActive(true);

        photoFileRepository.save(photoFile);

        // 11. Возвращаем DTO ответа
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

        Files.deleteIfExists(Paths.get(photoFile.getFilePath()));

        photoFileRepository.delete(photoFile);

    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex);
        }

        return "";

    }

    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created upload directory: {}", uploadPath);
            }
        } catch (IOException e) {
            log.error("Could not create upload directory", e);
        }
    }

    /**
     * Проверяет допустимость расширения для изображений
     */
    private boolean isValidImageExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }

        String ext = extension.toLowerCase();
        return ext.equals(".jpg") || ext.equals(".jpeg") ||
                ext.equals(".png") || ext.equals(".gif") ||
                ext.equals(".bmp") || ext.equals(".webp") ||
                ext.equals(".jfif");
    }

    /**
     * Очищает имя файла от недопустимых символов
     */

    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "";
        }

        // Удаляем все, кроме букв, цифр, точек, дефисов и подчеркиваний
        String clean = filename
                .replaceAll("[^a-zA-Zа-яА-ЯёЁ0-9._-]", "_")
                .replaceAll("\\s+", "_");

        // Убираем множественные подчеркивания
        clean = clean.replaceAll("_{2,}", "_");

        // Убираем подчеркивания в начале и конце
        clean = clean.replaceAll("^_+|_+$", "");

        // Если после очистки строка пустая, генерируем имя по умолчанию
        if (clean.isEmpty()) {
            clean = "photo_" + System.currentTimeMillis();
        }

        return clean;
    }

    /**
     * Обеспечивает уникальность имени файла
     */
    private String ensureUniqueFilename(String filename, Path directory) throws IOException {
        String nameWithoutExt = filename;
        String extension = "";

        if (filename.contains(".")) {
            int dotIndex = filename.lastIndexOf('.');
            nameWithoutExt = filename.substring(0, dotIndex);
            extension = filename.substring(dotIndex);
        }

        String uniqueFilename = filename;
        int counter = 1;

        while (Files.exists(directory.resolve(uniqueFilename))) {
            uniqueFilename = nameWithoutExt + "_" + counter + extension;
            counter++;

            // Защита от бесконечного цикла
            if (counter > 100) {
                uniqueFilename = nameWithoutExt + "_" + System.currentTimeMillis() + extension;
                break;
            }
        }

        return uniqueFilename;
    }

    /**
     * Получает абсолютный путь к директории загрузки
     */

    private Path getUploadPath() throws IOException {
        Path uploadPath;

        // Если путь абсолютный (содержит : или начинается с /)
        if (uploadDir.contains(":") || uploadDir.startsWith("/") || uploadDir.startsWith("\\")) {
            uploadPath = Paths.get(uploadDir);
        } else {
            // Относительный путь - относительно корня проекта
            String projectRoot = System.getProperty("user.dir");
            uploadPath = Paths.get(projectRoot, uploadDir);
        }

        // Создаем директорию если не существует
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return uploadPath;
    }

    /**
     * Создает поддиректорию по дате (год/месяц)
     */
    private Path createDateSubdirectory(Path basePath) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        Path datePath = basePath.resolve(year).resolve(month);

        if (!Files.exists(datePath)) {
            Files.createDirectories(datePath);
        }

        return datePath;
    }

    /**
     * Получает относительный путь (для URL)
     */

    private String getRelativePath(Path absolutePath) {
        try {
            Path projectRoot = Paths.get(System.getProperty("user.dir"));
            return projectRoot.relativize(absolutePath).toString().replace("\\", "/");
        } catch (Exception e) {
            // Если не удалось получить относительный путь, возвращаем только имя файла
            return absolutePath.getFileName().toString();
        }
    }

}
