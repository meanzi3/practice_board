package com.minzi.practice_board.service.posts;

import com.minzi.practice_board.domain.posts.Posts;
import com.minzi.practice_board.domain.posts.PostsRepository;
import com.minzi.practice_board.web.dto.PostsResponseDto;
import com.minzi.practice_board.web.dto.PostsSaveRequestDto;
import com.minzi.practice_board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final 이 선언된 모든 필드를 인자값으로 하는 생성하를 생성한다.
@Service // Service 임을 나타냄
public class PostsService {
  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto){
    return postsRepository.save(requestDto.toEntity()).getId();
  }
  // dto 로 맏은 데이터를 엔티티로 리포지토리에 저장한 후 id값을 반환

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto){
    Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
    posts.update(requestDto.getTitle(), requestDto.getContent());

    return id;
  }

  public PostsResponseDto findById(Long id){
    Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

    return new PostsResponseDto(entity);
  }
}
