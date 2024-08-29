package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Models.Image;

public class ImageMapper {

    public static ImageDto toImageDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setFileType(image.getFileType());
        dto.setData(image.getData());

        return dto;
    }

    public Image toImage(ImageDto imageDto) {
        Image image = new Image();
        image.setId(imageDto.getId());
        image.setFileType(imageDto.getFileType());
        image.setData(imageDto.getData());
        return image;
    }

}
