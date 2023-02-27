package com.minzi.practice_board.service.posts;

import antlr.collections.impl.LList;
import com.minzi.practice_board.domain.posts.Posts;
import com.minzi.practice_board.domain.posts.PostsRepository;
import com.minzi.practice_board.web.dto.PostsListResponseDto;
import com.minzi.practice_board.web.dto.PostsResponseDto;
import com.minzi.practice_board.web.dto.PostsSaveRequestDto;
import com.minzi.practice_board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

  @Transactional
  public void delete(Long id){
    Posts posts = postsRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

    postsRepository.delete(posts);
  }

  public PostsResponseDto findById(Long id){
    Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

    return new PostsResponseDto(entity);
  }

  @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨둠 (조회 속도가 개선되기 때문에 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하기 좋음)
  public List<PostsListResponseDto>  findAllDesc() {
    return postsRepository.findAllDesc().stream()
            .map(PostsListResponseDto::new)
            .collect(Collectors.toList());
    // postsRepository.findAllDesc 의 결과로 넘어온 Posts의 Stream을 map을 이용해 PostsListResponseDto로 변환 -> List로 변환
  }
}
