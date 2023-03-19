package com.project.board.author.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 세션이랑 로그인이 성공했을 때 스프링 서버가 사용자에게 돌려주는 임시로 할당한 저장 공간
        HttpSession session = request.getSession();
        session.setAttribute("greeting", authentication.getName()+ "님 반갑습니다.");
        response.sendRedirect("/");
    }
}