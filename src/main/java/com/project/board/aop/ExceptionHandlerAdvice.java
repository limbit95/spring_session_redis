package com.project.board.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

// AOP(Ascpect Oriented Programming) : 어떤 관점을 기준으로 로직을 모듈화한다는 것
// 이 클래스는 예외처리 또는 로그 등 공통화가 필요한 사오항을 모듈화 시킨 AOP 프로그램
// ControllerAdvice는 컨트롤러 단위에서. 특정한 event가 발생했을 때 catch 하는 역할
@ControllerAdvice
public class ExceptionHandlerAdvice {

    // @ExceptionHandler() 괄호 안에 해당 이름을 가진 예외가 발생했을 때 catch 하는 역할
    // 아래는 EntityNotFoundException 이라는 에러가 발생했을 때
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e){
        String context = "<header> <h1><span>존재하지 않는 화면입니다.</span></h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.NOT_FOUND);
    }

    // test용
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e){
        String context = "<header> <h1><span>에러가 발생했습니다.</span></h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
//    public ResponseEntity<String> unAuthorized(Exception e){
//        String context = "<header> <h1><span>로그인이 되지 않았습니다.</span></h1></header>";
//        return new ResponseEntity<String>(context, HttpStatus.UNAUTHORIZED);
//    }

}
