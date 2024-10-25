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

/**
 * application.properties
 * spring.aop.proxy-target-class=true CGLIB로 프록시 생성
 * spring.aop.proxy-target-class=false JDK 동적 프록시로 프록시 생성
 */
@Slf4j
// @SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK 동적 프록시로 프록시 생성
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB로 프록시 생성(properties를 생략해도 됨)
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {
	@Autowired
	MemberService memberService;

	@Test
	void success() {
		log.info("memberService proxy={}", memberService.getClass());
		memberService.hello("helloA");
	}

	@Slf4j
	@Aspect
	static class ThisTargetAspect {
		@Around("this(hello.aop.order.aop.member.MemberService)")
		public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[this-interface] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("target(hello.aop.order.aop.member.MemberService)")
		public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[target-interface] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("this(hello.aop.order.aop.member.MemberServiceImpl)")
		public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[this-implement] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("target(hello.aop.order.aop.member.MemberServiceImpl)")
		public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[target-implement] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}
	}
}
