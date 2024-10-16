package hello.proxy.config.v2_dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceBasicHandler implements InvocationHandler {

	private final Object target; // 프록시에서 호출할 실제 대상 클래스
	private final LogTrace logTrace;

	public LogTraceBasicHandler(Object target, LogTrace logTrace) {
		this.target = target;
		this.logTrace = logTrace;
	}

	// 인터페이스를 사용하여 JDK 기븐 프록시 구현
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		TraceStatus status = null;

		try {
			// method 메타 정보를 호출하여 호출된 클래스 이름과 메서드 명을 추출한다.
			String simpleName = method.getDeclaringClass().getSimpleName(); // 호출한 메서드가 정의된 클래스
			String name = method.getName(); // 호출한 메서드 명
			status = logTrace.begin(simpleName + "." + name);

			// 타깃 클래스 호출
			Object result = method.invoke(target, args);

			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}
}
