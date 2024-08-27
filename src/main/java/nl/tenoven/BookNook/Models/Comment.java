package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private byte Rating;
    private String User;
    private String Message;
    private String DatePosted;

    public Comment() {
    }

    public Comment(long id, byte rating, String user, String message, String datePosted) {
        Id = id;
        Rating = rating;
        User = user;
        Message = message;
        DatePosted = datePosted;
    }
}
