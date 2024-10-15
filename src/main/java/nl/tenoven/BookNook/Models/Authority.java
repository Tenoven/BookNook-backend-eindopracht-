//package nl.tenoven.BookNook.Models;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@IdClass(AuthorityKey.class)
//@Table(name = "authorities")
//@Getter
//@Setter
//public class Authority {
//    @Id
//    @Column(nullable = false)
//    private String username;
//
//    @Id
//    @Column(nullable = false)
//    private String authority;
//
//    public Authority() {}
//    public Authority(String username, String authority) {
//        this.username = username;
//        this.authority = authority;
//    }
//
//}

package nl.tenoven.BookNook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")
@Getter
@Setter
public class Authority {

    @Id
    @Column(nullable = false)
    private String username;  // Part of the composite key

    @Id
    @Column(nullable = false)
    private String authority;  // Part of the composite key

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)  // Map the username to User entity
    private User user;  // Link to the User entity

    public Authority() {}

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
