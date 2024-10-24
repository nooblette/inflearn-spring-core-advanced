package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV2 {

	// hello.aop.order 패키지와 하위 패키지에 어드바이스(Advice)를 적용
	@Pointcut("execution(* hello.aop.order..*(..))")
	private void allOrder() {} // pointcut signature

	@Around("allOrder()") // allOrder() pointcut signature을 사용한다.
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니쳐
		return joinPoint.proceed(); // 실제 타깃 클래스 호출
	}
}
