package com.project.board;

import com.project.board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity 이 어노테이션을 통해 Security customizing 기능 활성화
// WebSecurityConfigurerAdapter 라는 클래스를 상속하였으나, 스프링 버전 2.7대 이상에서는 depreacted됨
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // 스프링 빈을 만드는 방법 2가지
    // component는 setter 나 builder 등을 통해 사용자가 특정값을 변경해서 생성한 인스턴스를 스프링에게 관리하라고 맡김
    // 1) 개발자가 직접 컨트롤이 가능한 내부 클래스에서 사용가능
    // 2) class 에서만 선언 가능한 어노테이션이다
    
    // @Bean 클래스를 스프링한테 알아서 인스턴스를 생성한 후에 등록하라고 맡기는 것
    // 1) 개발자가 컨트롤이 불가능한 외부 라이브러리 사용시 사용
    // 2) 메서드단에도 붙일 수 있고, 이 때 클래스에는 @Configuration을 붙여줘야 함
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 암호화 모듈을 우리 프로젝트의 스프링에 빈으로 주입하는 것
        // 스프링 빈이란 : 싱글톤, 상시적으로 떠있는 객체
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // csrf라는 공격에 대한 설정을 현재는 필요 없으니 일단 disable
                .csrf().disable()
                .authorizeRequests()
                // antMatchers : 특정 url에는 접속권한을 모두 허용한 것 (로그인 검사 할 필요 없는 화면들 선언)
                .antMatchers("/author/login", "/", "/author/createform", "/author/create")
                .permitAll()
                // 그 외에 나머지 request에 대해서는 인증을 요구하도록 하는 것
                .anyRequest().authenticated()
                .and()
                .formLogin() // 로그인 홈페이지
                // login에 대한 page url 지정
                    .loginPage("/author/login")
                // login 화면에서 어떤 post 요청을 통해 로그인을 시도할 것인지 지정
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/doLogout")
                .and().build();
    }

}