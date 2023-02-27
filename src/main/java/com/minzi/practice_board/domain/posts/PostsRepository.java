package com.minzi.practice_board.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

  @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
  List<Posts> findAllDesc();
  // posts 객체를 id 값 기준으로 내림차순으로 정렬하여 findAllDesc 리스트로 저장
}