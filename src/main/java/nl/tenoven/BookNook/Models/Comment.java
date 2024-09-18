package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commenter;
    private String message;
    private String datePosted;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Comment() {
    }

    public Comment(Long id, String user, String message, String datePosted) {
        this.id = id;
        this.commenter = user;
        this.message = message;
        this.datePosted = datePosted;
    }
}
