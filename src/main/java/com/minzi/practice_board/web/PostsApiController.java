package com.minzi.practice_board.web;


import com.minzi.practice_board.service.posts.PostsService;
import com.minzi.practice_board.web.dto.PostsResponseDto;
import com.minzi.practice_board.web.dto.PostsSaveRequestDto;
import com.minzi.practice_board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성한다.
@RestController // Controller 임을 알려주는 표시
public class PostsApiController {
  private final PostsService postsService;

  /**
   * 등록
   */
  @PostMapping("/api/v1/posts") // POST 통신 (함께 받아야 하는 데이터가 있으면, @RequestBody 활용)
  public Long save(@RequestBody PostsSaveRequestDto requestDto){
    return postsService.save(requestDto);
  }
  // @RequestBody를 이용해 HTTP Body에 담긴 데이터를 매핑하여 가지고 온다. 받은 데이터를 service에서 save

  /**
   * 수정
   */
  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
    return postsService.update(id, requestDto);
  }

  /**
   * 삭제
   */
  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id){
    postsService.delete(id);
    return id;
  }

  /**
   * id 별 조회
   */
  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    return postsService.findById(id);
  }

}
