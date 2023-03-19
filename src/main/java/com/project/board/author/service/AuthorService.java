package com.project.board.author.service;

import com.project.board.author.domain.Author;
import com.project.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService implements UserDetailsService {
    
    // SecurityConfig에서 만들어 놓은 스프링빈을 여기에 주입시키는 것
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author){
        authorRepository.save(author);
        // 이 시점에서는 save가 완료되지 않음, 모든 메서드가 완려되어야 commit이 됨
        // author.password = encode(author.password)
        author.encodePassword(passwordEncoder);
    }

    public Author findByEmail(String email){
        return authorRepository.findByEmail(email).orElse(null);
    }

    public Optional<Author> findById(Long id) throws Exception {
        // Day 35
//        try{
//            return authorRepository.findById(id).orElseThrow(Exception::new);
//        }catch(Exception e){
//            throw new Exception("Not Found Exception");
//        }

        return authorRepository.findById(id);
    }

    public List<Author> findAllFetchJoin(){
        return authorRepository.findAllFetchJoin();
    }

    // doLogin이 loadByUsername이라는 메서드를 찾는 걸로 약속이 되어 있음
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 할 때 입력한 email이 DB에 있는지 없는지 확인 먼저 한 후 있다면 밑에 return 단으로 넘어가서
        // 존재하는 해당 유저의 email과 password를 스프링에게 알려준다
        // 그래야 스프링이 로그인할 때 맞는지 비교할 데이터가 주어지는 것이니까
        Author author = authorRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email입니다."));
        // doLogin을 할 때 스프링이 사용자가 입력한 email을 DB에서 조회하여 가져온 author와
        // 사용자가 입력한 password를 비교할 수 있도록 author.getEmail(), author.getPassword() 을 리턴하는 것
        return new User(author.getEmail(), author.getPassword(), Collections.emptyList());
    }

}