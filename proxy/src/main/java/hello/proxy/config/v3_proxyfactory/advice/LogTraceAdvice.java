package hello.proxy.config.v3_proxyfactory.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceAdvice implements MethodInterceptor {
	// 스프링이 제공하는 어드바이스(Advice)를 사용하는 경우 타깃은 ProxyFactory에 정의된다.
	// 따라서 Advice에는 타깃 클래스에 의존할 필요가 없다.
	private final LogTrace logTrace;

	public LogTraceAdvice(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TraceStatus status = null;

		try {
			// invocation 에서 호출한 메서드명을 추출한다.
			Method method = invocation.getMethod();

			// method가 정의된 클래스명 추출
			String className = method.getDeclaringClass().getSimpleName();

			// 로그 메시지 생성
			String message = className + "." + method.getName() + "()";
			status = logTrace.begin(message);

			// 타깃 클래스 호출
			Object result = invocation.proceed();

			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}
}
