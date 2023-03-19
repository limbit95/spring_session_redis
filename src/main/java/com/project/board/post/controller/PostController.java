package com.project.board.post.controller;

import com.project.board.author.domain.Author;
import com.project.board.author.service.AuthorService;
import com.project.board.post.domain.Post;
import com.project.board.post.domain.PostDto;
import com.project.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;

    @Autowired
    public PostController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;
    }

    @GetMapping("/post/list")
    public String postList(Model model){
//        model.addAttribute("posts", this.postService.findAll());

        // 방법 1
        model.addAttribute("posts", this.postService.findByScheduled());

//        // 방법 2
//        List<Post> new_list = new ArrayList<>();
//        for(Post a : postService.findAll()){
//            if(a.getScheduled() == null){
//                new_list.add(a);
//            }
//        }
//        model.addAttribute("posts", new_list);

        return "/post/postList";
    }

    @GetMapping("/post/postCreateform")
    public String postCreateform(){
        return "/post/postCreate";
    }

    @PostMapping("/post/create")
    public String create(PostDto postDto
    ){
        Author author = authorService.findByEmail(postDto.getEmail());

        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println("schejuled : " + postDto.getScheduled());
        if(postDto.getScheduled() != null){
            String str = postDto.getScheduledTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(str, formatter);
        }

        Post post = Post.builder()
                .title(postDto.getTitle())
                .contents(postDto.getContents())
                .author(author)
                .scheduled(postDto.getScheduled())
                .scheduledTime(dateTime)
                .build();

        postService.save(post);

        return "redirect:/post/list";
    }

    @GetMapping("/post/findById")
    public String postDetail(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("post", this.postService.findById(id));

        return "/post/postDetail";
    }

    @GetMapping("/post/Delete")
    public String postDelete(@RequestParam(value = "id")Long id){
        this.postService.delete(id);
        return "redirect:/post/list";
    }

    // 현업에서는 delete 기능을 만들 때 @GetMaipping을 사용하지 않고 @DeleteMapping을 사용한다
    // 현재 이 프로젝트 기능으로서는 프론트엔드 단에서 get요청으로 들어오는데 밑의 코드는 DeleteMapping으로
    // 받고 있기 때문에 서로 연결이 되지 않아 실제 서버 페이지에서는 오류가 나는 것이나 실제 로직에는 문제 없음
//    @DeleteMapping("/post/delete")
//    public String postDelete(@RequestParam(value = "id")Long id){
//        this.postService.delete(id);
//        return "redirect:/";
//    }



}
