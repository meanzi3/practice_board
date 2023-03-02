package com.minzi.practice_board.config.auth.dto;

import com.minzi.practice_board.domain.user.Role;
import com.minzi.practice_board.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
    this.attributes = attributes;
    this. nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }   // OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 반환해야 함

  public User toEntity() {
    return User.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(Role.GUEST)
            .build();
  } // User 엔티티를 생성한다. / 처음 가입 할 때 엔티티를 생성하므로 가입할 때의 기본 권한은 GUEST 이다.
}
