package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.ImageDtos.ImageDto;
import nl.tenoven.BookNook.Dtos.ImageDtos.ImageInputDto;
import nl.tenoven.BookNook.Models.Image;

public class ImageMapper {

    public static ImageDto toImageDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setFileName(image.getFileName());

        return dto;
    }

    public Image toImage(ImageInputDto imageInputDto) {
        Image image = new Image();
        image.setFileName(imageInputDto.getFileName());
        return image;
    }

}
