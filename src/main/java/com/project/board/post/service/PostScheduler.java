//package com.project.board.post.service;
//
//import com.project.board.post.domain.Post;
//import com.project.board.post.repository.PostRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//// 스프링빈으로 등록하는 것
//@Component
//@Slf4j
//public class PostScheduler {
//
//    private final PostRepository postRepository;
//
//    public PostScheduler(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    @Scheduled(cron = "0 0/1 * * * *") // 초 분 시 일 월 요일
//    public void postSchedule(){
//        // 1. 현재 예약되어 있는 건들은 scheduled에 cheched이므로 checked인 건들만 조회
//        // 2. 현재 시간보다 scheduled_time이 시간상 뒤처진 건들은 null로 변경
//        // 특정건들에 대해서 repository.save for문을 돌려가면서
//
//
//        // repository.save 에서 기존에 데이터가 있으면 update, 없으면 알아서 insert가 됨
////        Post post = postRepository.findById(1L).orElse(null);
////        post.setScheduled(null);
////        postRepository.save(post);
//
//        log.info("hello");
//
//        List<Post> post = postRepository.findByScheduled("checked");
//        for(Post a : post){
//            if(a.getScheduledTime().isBefore(LocalDateTime.now()) == true){
//                a.setScheduled(null);
//                postRepository.save(a);
//            }
//        }
//    }
//
//}
