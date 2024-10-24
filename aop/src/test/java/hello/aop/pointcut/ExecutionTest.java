package hello.aop.pointcut;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionTest {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		// 출력 결과 : public java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
		// pointcut 지시자의 경로와 메서드명, 파라미터를 이 helloMethod 출력값과 매칭할 수 있다.
		log.info("helloMethod={}", helloMethod);
	}
}
