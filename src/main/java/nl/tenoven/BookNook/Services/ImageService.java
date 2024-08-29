package nl.tenoven.BookNook.Services;

import jakarta.persistence.EntityNotFoundException;
import nl.tenoven.BookNook.Models.Image;
import nl.tenoven.BookNook.Repositories.ImageRepository;

import java.util.List;

import static nl.tenoven.BookNook.Mappers.ImageMapper.toImageDto;

public class ImageService {
    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    public Image getImage(long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Image" + id + "not found"));
        return image;
    }

    public ImageDto addImage(Image newImage) {
        Image savedImage = imageRepository.save(newImage);
        return toImageDto(savedImage);
    }

    public ImageDto updateImage(long id, Image updatedImage) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Image" + id + "not found"));

        if (updatedImage.getFileType() != null) {
            image.setFileType(updatedImage.getFileType());
        }
        if (updatedImage.getData() != null) {
            image.setData(updatedImage.getData());
        }


        Image savedImage= imageRepository.save(image);
        return toImageDto(savedImage);
    }

    public void deleteImage(long id) {
        if (!imageRepository.existsById(id)) {
            throw new EntityNotFoundException("Image" + id + "not found");
        }
        imageRepository.deleteById(id);
    }

}
