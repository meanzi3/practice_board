package com.minzi.practice_board.web;

import com.minzi.practice_board.service.posts.PostsService;
import com.minzi.practice_board.web.dto.PostsResponseDto;
import com.minzi.practice_board.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  /**
   * 기본 페이지 (게시글 목록 조회)
   */
  @GetMapping("/")
  public String index(Model model){
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }
  // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
  // postsService.findAllDesc() 로 가져 온 결과를 "posts"라는 이름으로 index.html에 전달

  /**
   * 게시글 등록 페이지
   */
  @GetMapping("/posts/save")
  public String postsSave(){
    return "posts-save";
  }

  /**
   * 게시글 수정 페이지
   */
  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model){
    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);

    return "posts-update";
  }
  // id로 해당 게시글을 찾아 dto로 담고 "post" 라는 이름으로 posts-update.html로 전달
}
