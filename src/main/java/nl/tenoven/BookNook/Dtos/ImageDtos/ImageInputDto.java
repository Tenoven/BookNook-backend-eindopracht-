package nl.tenoven.BookNook.Dtos.ImageDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;

@Getter
@Setter
public class ImageInputDto {
    @NotEmpty
    private Image.FileType fileType;
    @NotEmpty
    private byte[] Data;

    public ImageInputDto(Image.FileType fileType, byte[] data) {
        this.fileType = fileType;
        Data = data;
    }
}
