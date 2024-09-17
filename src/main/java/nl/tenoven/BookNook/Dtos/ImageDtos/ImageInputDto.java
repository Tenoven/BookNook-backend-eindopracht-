package nl.tenoven.BookNook.Dtos.ImageDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import nl.tenoven.BookNook.Models.Image;

@Getter
@Setter
public class ImageInputDto {
    @NotEmpty
    private String fileName;

}
