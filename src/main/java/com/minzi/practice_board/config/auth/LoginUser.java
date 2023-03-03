package com.minzi.practice_board.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 어노테이션이 생성될 수 있는 위치를 지정한다. -> 여기서는 파라미터로 지정했으니 메소드의 파라미터로 선언된 객체에서 사용이 가능하다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // 어노테이션 클래스로 지정. LoginUser 라는 이름을 가진 어노테이션이 생성된다. 
}
