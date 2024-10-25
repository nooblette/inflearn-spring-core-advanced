package hello.aop.exam.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class RetryAspect {
	// @Retry 어노테이션이 있는 메서드가 호출되기 전에 실행할 Advice 작성
	// 파라미터로 retry를 받는 경우 포인트컷 지시자에서는 변수명만 작성하면 된다.(메서드 시그니처 보고 retry의 타입을 유추할 수 있다.)
	@Around("@annotation(retry)") // 재시도하면서 joinPoint 시점을 개발자가 알아야한다. 따라서 Around로 선언한다.
	public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
		log.info("[retry] {} retry={}", joinPoint.getSignature().getName(), retry);

		int maxRetry = retry.value();
		Exception exceptionHolder = null;

		// 타깃 클래스 호출
		for (int retryCount = 0; retryCount <= maxRetry; retryCount++) {
			try {
				log.info("[retry] try count={}/{}", retryCount, maxRetry);
				return joinPoint.proceed();
			} catch (Exception e) {
				exceptionHolder = e;
			}
		}

		// 재처리 횟수를 초과한 경우 발생한 경우, 발생한 예외를 외부로 던진다.
		assert exceptionHolder != null;
		throw exceptionHolder;
	}
}
