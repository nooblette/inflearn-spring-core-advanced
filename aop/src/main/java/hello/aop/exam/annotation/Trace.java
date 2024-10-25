package hello.aop.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어노테이션 대상, 여기에서는 메서드에 어노테이션을 건다.
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 생명 주기, 여기에서는 런타임 시점에도 어노테이션이 생존한다.
public @interface Trace {
}
