package hello.aop.pointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.order.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import({AtAnnotationTest.AtAnnotationAspect.class}) // Aspect도 스프링 빈으로 등록해야 적용됨
public class AtAnnotationTest {
	@Autowired
	MemberService memberService;

	@Test
	void success() {
		log.info("memberService proxy={}", memberService.getClass());
		memberService.hello("hello");
	}

	@Slf4j
	@Aspect
	static class AtAnnotationAspect {
		// @methodAop 어노테이션이 있는 곳에 Advice를 적용한다.
		@Around("@annotation(hello.aop.order.aop.member.annotation.MethodAop)")
		public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[@annotation] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}
	}
}
