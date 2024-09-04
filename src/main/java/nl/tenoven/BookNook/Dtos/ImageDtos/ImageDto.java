package nl.tenoven.BookNook.Dtos.ImageDtos;

import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;

@Getter
@Setter
public class ImageDto {
    private long Id;
    private Image.FileType fileType;
    private byte[] Data;

    public ImageDto() {
    }
}
