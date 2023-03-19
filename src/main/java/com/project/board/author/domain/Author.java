package com.project.board.author.domain;

import com.project.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Setter
// 현업에서는 Setter를 거의 사용하지 않음 대신 생성자 방식을 많이 사용함
// 생성자의 단점으로 인해 생성자와 Setter의 장점을 취한 Builder 방식을 가장 많이 사용
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 50, unique = true)
    private String email;
    private String password;

    private String role;
    private LocalDateTime createDate;

//    // AuthorController의 authorDetail의 방법 1
//    @Transient
//    @Setter
//    private int count;

    // AuthorController의 authorDetail의 방법 2
    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Author(String name, String email, String password, String role, LocalDateTime createDate){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = createDate.now();
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

}
