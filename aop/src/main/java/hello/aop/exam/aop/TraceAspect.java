package hello.aop.exam.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class TraceAspect {
	// @Trace 어노테이션이 있는 메서드가 호출되기 전에 실행할 Advice 작성
	@Before("@annotation(hello.aop.exam.annotation.Trace)")
	public void doTrace(JoinPoint joinPoint) {
		// 타깃 클래스에 넘어가는 파라미터 정보를 모두 꺼낸다.
		Object[] args = joinPoint.getArgs();
		log.info("[trace] {}, args={}", joinPoint.getSignature().getName(), args);
	}
}
