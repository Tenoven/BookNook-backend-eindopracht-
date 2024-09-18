package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Dtos.ImageDtos.ImageDto;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Service
public class ImageService {
    private final Path fileStoragePath;
    private final  String fileStorageLocation;
    private final ImageRepository imageRepository;

    public ImageService(@Value("${my.upload_location}") String fileStorageLocation, ImageRepository imageRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.imageRepository = imageRepository;
        Files.createDirectories(fileStoragePath);
    }

    public String addImage(MultipartFile file) throws IOException{

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + FileSystems.getDefault().getSeparator() + fileName );

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        imageRepository.save(new Image(fileName));
        return fileName;
    }

    public Resource getImage(String fileName) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }
}
