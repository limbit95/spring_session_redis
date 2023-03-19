package com.project.board.author.repository;

import com.project.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository <Author, Long> {

    Optional<Author> findByEmail(String email);

    // jpql을 통해 raw 쿼리를 생성
    // jpql의 문법은 sql의 문법과는 조금 다름
    @Query("select distinct a from Author a left join fetch a.posts")
    List<Author> findAllFetchJoin();
    
}