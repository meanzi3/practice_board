package com.minzi.practice_board.config.auth;

import com.minzi.practice_board.config.auth.dto.OAuthAttributes;
import com.minzi.practice_board.config.auth.dto.SessionUser;
import com.minzi.practice_board.domain.user.User;
import com.minzi.practice_board.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final UserRepository userRepository;
  private final HttpSession httpSession;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드이다. (네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용)
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    // OAuth2 로그인 시 키가 되는 필드 값(Primary Key와 같이) (구글에서는 기본 코드가 "sub" 이다. / 네이버, 카카오는 지원X)

    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
    // OAuth2UserService를 통해 가져온 OAuth2User의 Attribute를 담을 클래스이다.

    User user = saveOrUpdate(attributes);

    httpSession.setAttribute("user", new SessionUser(user));
    // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.

    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
            attributes.getAttributes(),
            attributes.getNameAttributeKey());
  }

  private User saveOrUpdate(OAuthAttributes attributes) {
    User user = userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
            .orElse(attributes.toEntity());
    return userRepository.save(user);
  }
  // 구글 사용자 정보가 업데이트 되었ㅇ르 때를 대비하여 update 기능 구현 -> 사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영하도록
}
