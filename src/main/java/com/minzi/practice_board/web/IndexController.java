package com.minzi.practice_board.web;

import com.minzi.practice_board.config.auth.LoginUser;
import com.minzi.practice_board.config.auth.dto.SessionUser;
import com.minzi.practice_board.service.posts.PostsService;
import com.minzi.practice_board.web.dto.PostsResponseDto;
import com.minzi.practice_board.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;
  private final HttpSession httpSession;

  /**
   * 기본 페이지 (게시글 목록 조회)
   */
  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user){
    model.addAttribute("posts", postsService.findAllDesc());
    if(user != null){
      model.addAttribute("userName",user.getName());
      // 세션에 저장된 값이 있을 때만 model 에 userName으로 등록한다.
    }
    return "index";
  }
  // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
  // postsService.findAllDesc() 로 가져 온 결과를 "posts"라는 이름으로 index.html에 전달

  /**
   * 게시글 등록 페이지
   */
  @GetMapping("/posts/save")
  public String postsSave(Model model,@LoginUser SessionUser user){
    model.addAttribute("userName",user.getName());
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

  // 로그인 페이지
  @GetMapping("/oauth-login")
  public String login(){
    return "login";
  }
}
