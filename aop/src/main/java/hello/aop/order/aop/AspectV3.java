package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV3 {

	// hello.aop.order 패키지와 하위 패키지에 어드바이스(Advice)를 적용
	@Pointcut("execution(* hello.aop.order..*(..))")
	private void allOrder() {} // pointcut signature

	// 클래스 이름 패턴이 *Service인 곳에 어드바이스(Advice) 적용 - 트랜잭션은 보통 비즈니스 로직(서비스 계층)에 두기 때문
	@Pointcut("execution(* *..*Service.*(..))")
	private void allService() {}

	@Around("allOrder()") // allOrder() pointcut signature을 사용한다.
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니쳐
		return joinPoint.proceed(); // 실제 타깃 클래스 호출
	}

	// hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
	@Around("allOrder() && allService()") // && 키워드로 포인트컷(Pointcut)을 여러개(and 조건)으로 둘 수 있다.
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			log.info("[트랜잭션 시작] {}", joinPoint.getSignature());

			// 실제 비즈니스 로직 실행
			Object result = joinPoint.proceed();
			log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

			return result;
		} catch (Exception e) {
			log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
			throw e;
		} finally {
			log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
		}
	}
}
