package com.project.board.author.controller;

import com.project.board.author.domain.Author;
import com.project.board.author.domain.AuthorDto;
import com.project.board.author.service.AuthorService;
import com.project.board.post.domain.Post;
import com.project.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@Slf4j
public class AuthorController {

    private final AuthorService authorService;
    private final PostService postService;

//    // logger 생성 방법 1(Day36)
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    // logger 생성 방법 2
    // class 명 위에 @Slf4j 붙이기

    @Autowired
    public AuthorController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    // Day 36
    @GetMapping("/test")
    public void test(){
        // System println 로그
        System.out.println("test log");

//        // slf4j 라이브러리를 활용한 현업에서 사용하는 logging 방식
//        // 방법 1
//        logger.trace("test trace log");
//        logger.debug("test debug log");
//        logger.info("test info log");
//        logger.error("test error log");

        // 방법 2
        log.trace("test trace log");
        log.debug("test debug log");
        log.info("test info log");
        log.error("test error log");
    }

    @GetMapping("/author/list")
    public String authorList(Model model) {
        // Day 35(2023.03.11) findAllFetchjoin 쿼리문 관련
        List<Author> authors = authorService.findAllFetchJoin();
        model.addAttribute("authors", authors);

//        model.addAttribute("authors", this.authorService.findAll());
        // 화면을 렌더링하기 전에 domain을 넣어서
        return "author/authorList";
    }

    @GetMapping("/author/createform")
    public String createForm() {

        return "/author/authorCreate";
    }

    @PostMapping("/author/create")
    public String create(AuthorDto authorDto) {
//        // 방법1 : setter, 단점은 setter 사용을 여기저기 객체에 변경을 가할 수 있어 유지보수가 힘들어짐
//        Author author = new Author();
//        author.setName(authorDto.getName());
//        author.setEmail(authorDto.getEmail());
//        author.setPassword(authorDto.getPassword());
//        author.setRole(authorDto.getRole());
//        author.setCreateDate(LocalDateTime.now());

//        // 방법2 : 생성자를 통한 객체 생성 방식
//        // 문제점 : 가독성이 떨어지다보니, 실수할 여지 있고, 각 변수 자리에 맞게 세팅해줘야 정확한 세팅이 되는 문제점
//        Author author = new Author(authorDto.getName(), authorDto.getEmail(), authorDto.getPassword(), authorDto.getRole());

        // 방법 3 : Builder를 통한 객체 생성 방식
        Author author = Author.builder().name(authorDto.getName())
                .email(authorDto.getEmail())
                .password(authorDto.getPassword())
                .role(authorDto.getRole())
                .build();

        authorService.save(author);

        return "redirect:/";

    }

    @GetMapping("/author/findById")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String authorDetail(@RequestParam(value = "id") Long id, Model model) throws Exception {

//        // 방법 1
//        Author author =this.authorService.findById(id);
//        List<Post> posts = postService.findByAuthor_id(id);
//        author.setCount(posts.size());

//        // 방법 1을 사용할 때 필요한 코드
//        model.addAttribute("details", this.authorService.findById(id));

        // 방법 2 : author 객체를 최초 조회할 때부터 post와 join을 걸어 가져온다
//        Author author = authorService.findById(id);

        // 방법 3 :
//        List<Author> authors = authorService.findAllFetchJoin(id);

//        model.addAttribute("details", author); // 위의 어떤 방법을 사용하든 고정값


        // Day35 예외처리 관련
        // orElseGet.(null) 일 경우 controller -> 화면 까지 null이 리턴되어 에러 발생
        // orElseThrow(Exception::new) 일 경우 service 클래스에서 Exception이 발생하여 여기서 멈춤

        // 1단계 개선 : Spring Error 로그를 제대로 찍어보기
        // 2단계 개선 : AOP를 활용해 공통 모듈에서 예외를 일괄처리
        try{
            Author author = authorService.findById(id).orElseThrow(EntityNotFoundException::new);
            model.addAttribute("details", author);
        }catch (EntityNotFoundException e){
            log.error("findById error : " + e.getMessage());
            throw new EntityNotFoundException("Not Found Exception");
        }


        return "/author/authorDetail";
    }


    // 2023.03.19 Day38
    @GetMapping("/author/login")
    public String login(){
        return "/author/loginPage";
    }
    

}