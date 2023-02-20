package com.minzi.practice_board.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타낸다.
public class Posts {

  @Id // 해당 테이블의 PK(Primary Key) 필드를 나타낸다.
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // PK의 생성 규칙을 나타낸다. GenerationType.IDENTITY 옵션을 추가해야 auto_increment가 된다.
  private Long id;

  @Column(length = 500, nullable = false)
  // 테이블의 칼럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 칼럼이 되지만
  // 기본값 이외에 추가로 변경이 필요한 옵션이 있을 시에 사용한다.
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  @Builder // 해당 클래스의 빌더 패턴 클래스 생성. 생상자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함한다.
  public Posts(String title, String content, String author){
    this.title = title;
    this.content = content;
    this.author = author;
  }
}
