package com.minzi.practice_board.domain.posts;

import com.minzi.practice_board.domain.posts.Posts;
import com.minzi.practice_board.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // 별다른 설정 없이 SpringBootTest 사용 시 H2 데이터베이스를 자동 실행
public class PostsRepositoryTest {

  @Autowired
  PostsRepository postsRepository;

  @After // JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드
  public void cleanup() {
    postsRepository.deleteAll();
  }

  @Test
  public void 게시글저장_불러오기(){
    // given
    String title = "테스트 게시글";
    String content = "테스트 본문";

    postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("aaa@gmail.com")
            .build()); // posts에 insert(id값이 있으면)/update(id값이 없으면 쿼리를 실행한다.

    // when
    List<Posts> postsList = postsRepository.findAll(); // posts에 있는 모든 데이터를 조회해 list로 저장

    // then
    Posts posts = postsList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

  @Test
  public void BaseTimeEntity_등록(){
    // given
    LocalDateTime now = LocalDateTime.now();
    postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());

    // when
    List<Posts> postsList = postsRepository.findAll();

    // then
    Posts posts = postsList.get(0);

    System.out.println(">>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

    assertThat(posts.getCreatedDate()).isAfter(now);
    assertThat(posts.getModifiedDate()).isAfter(now);
  }
}