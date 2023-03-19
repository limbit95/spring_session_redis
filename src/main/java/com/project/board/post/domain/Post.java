package com.project.board.post.domain;

import com.project.board.author.domain.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String title;
    private String contents;
    private LocalDateTime createDate;

    // Lazy는 author 객체를 사용하는 곳에서만 author 객체를 조회해서 가져온다
    // 그러나 Lazy를 걸지 않으면 기본 타입 eager인데, eager는 무조건 참조객체를 조회해서 가져온다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "authorId", referencedColumnName = "id")
    private Author author;

    @Setter
    private String scheduled;

    private LocalDateTime scheduledTime;

    @Builder
    public Post(String title, String contents, Author author, String scheduled, LocalDateTime scheduledTime){
        this.title = title;
        this.contents = contents;
        this.createDate = createDate.now();
        this.author = author;
        this.scheduled = scheduled;
        this.scheduledTime = scheduledTime;
    }


}
