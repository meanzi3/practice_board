package com.minzi.practice_board.config.auth.dto;

import com.minzi.practice_board.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
  private String name;
  private String email;
  private String picture;

  public SessionUser(User user){
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
  }

  // SessinUser 에는 인증된 사용자 정보만 있으면 된다. (name, email, picture 만 선언)
}
