package hello.proxy.config.v6_aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect // 어드바이저(Advisor)를 편리하게 생성하기 위함
public class LogTraceAspect {
	private final LogTrace logTrace;

	public LogTraceAspect(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Around("execution(* hello.proxy.app..*(..))") // pointcut
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		// advice 로직 작성
		TraceStatus status = null;

		try {
			// invocation 에서 호출한 메서드명을 추출한다.
			String message = joinPoint.getSignature().toShortString();
			status = logTrace.begin(message);

			// 타깃 클래스 호출
			Object result = joinPoint.proceed();

			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}
}
