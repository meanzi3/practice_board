package com.minzi.practice_board.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

  GUEST("ROLE_GUEST", "손님"),
  USER("ROLE_USER","일반 사용자");
  // Spring security에는 권한 코드에 항상 ROLE_ 이 앞에 있어야 함

  private final String key;
  private final String title;
}
