package com.project.board.post.service;

import com.project.board.post.domain.Post;
import com.project.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(Post post){
        postRepository.save(post);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findById(Long id){
        return postRepository.findById(id).orElseGet(null);
    }

    public void delete(Long id){
//        Post post = postRepository.findById(id).orElse(null);
        postRepository.delete(findById(id));
    }

    public List<Post> findByAuthor_id(Long author_id){
        return postRepository.findByAuthor_id(author_id);
    }

    public List<Post> findByScheduled(){
        return postRepository.findByScheduled(null);
    }

}
