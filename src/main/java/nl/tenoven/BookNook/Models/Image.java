package nl.tenoven.BookNook.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "image")
@Entity
public class Image {
    @Id
    private String fileName;

    public Image(String fileName) {
        this.fileName = fileName;
    }

    public Image() {
    }

}
