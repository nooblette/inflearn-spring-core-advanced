package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV4Pointcut {

	// 외부 포인트 컷 사용 - 패키지명을 포함한 클래스 이름과 포인트컷 시그니처를 모두 지정한다.
	@Around("hello.aop.order.aop.Pointcuts.allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니쳐
		return joinPoint.proceed(); // 실제 타깃 클래스 호출
	}

	@Around("hello.aop.order.aop.Pointcuts.allOrderAndService()")
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
