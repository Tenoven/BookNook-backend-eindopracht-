package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "image")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long Id;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private byte[] Data;


    public enum FileType { JPG, jpg, PNG, png}

}
