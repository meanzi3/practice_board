package com.minzi.practice_board.domain.user;

import com.minzi.practice_board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  @Enumerated(EnumType.STRING) // JPA로 데이터베이스로 저장할 때, Enum 값을 어떤 형태로 저장할 지를 결정한다.
  // 기본적으로는 int 형으로 저장되는데, DB에서 확인할 때는 그 값이 어떤 코드를 의미하는지 알 수 없기 때문에 문자열로 저장하도록 선언하였다.
  @Column(nullable = true)
  private Role role;

  @Builder
  public User(String name, String email, String picture, Role role){
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public User update(String name, String picture){
    this.name = name;
    this.picture = picture;

    return this;
  }

  public String getRoleKey(){
    return this.role.getKey();
  }
}
