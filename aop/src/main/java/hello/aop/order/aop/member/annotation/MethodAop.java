package hello.aop.order.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // method 레벨에 붙이는 어노테이션
@Retention(RetentionPolicy.RUNTIME) // 애플리케이션이 runtime(실행시점)까지 어노테이션이 살아있음 (동적으로 실행시점에 어노테이션을 읽을 수 있음)
public @interface MethodAop {
	String value(); // 애노테이션도 값을 가질 수 있다.
}
