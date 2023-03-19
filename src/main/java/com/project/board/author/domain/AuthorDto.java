package com.project.board.author.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
