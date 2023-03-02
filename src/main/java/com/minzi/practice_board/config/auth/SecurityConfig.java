package com.minzi.practice_board.config.auth;

import com.minzi.practice_board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http.csrf().disable()
            .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 함
            .and()
            .authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점. 이게 선언되어야만 andMatchers 옵션 사용 가능
            .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").hasRole(Role.USER.name())
            // antMatchers : 권한 관리 대상을 지정하는 옵션으로 URL, HTTP 메소드 별로 관리가 가능하다.
            // 지정된 URL들은 permitAll() 옵션을 주어 전체 열람 권한을 줌.
            // /api/v1/** 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함
            .anyRequest().authenticated() // 설정된 값들 외의 URL들은 모두 인증도니 사용자들에게만 허용하도록
            .and()
            .logout().logoutSuccessUrl("/") // 로그아웃 기능에 대한 여러 설정의 진입접을 설정 -> 여기서는 로그아웃 성공 시 / 로 이동
            .and()
            .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
            // oauth2Login() : OAuth2 로그인 기능에 대한 여러 설정의 진입점
            // userInfoEndpoint() : OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
            // userService : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록 ( 각 페이지 별로 어떤 권한을 가진 사용자에게 보이게 할 것인지 설정)
  }
}
