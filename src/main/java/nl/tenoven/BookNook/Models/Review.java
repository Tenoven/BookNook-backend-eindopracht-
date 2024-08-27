package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Revieuws")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String User;
    private String Comments;
    private String Book;
    private String Text;
    private byte Score;
    private boolean Validated;

    public Review() {
    }

    public Review(long Id, String User, String Comments, String Book, String Text, byte Score) {
        this.Id = Id;
        this.User = User;
        this.Comments = Comments;
        this.Book = Book;
        this.Text = Text;
        this.Score = Score;
    }



}
