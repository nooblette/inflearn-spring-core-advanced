package hello.proxy.config.v2_dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.PatternMatchUtils;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceFilterHandler implements InvocationHandler {

	private final Object target;
	private final LogTrace logTrace;
	private final String[] patterns; // 메서드 명이 이 패턴에 적용될때만 프록시가 동작하여 로그를 남긴다.

	public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
		this.target = target;
		this.logTrace = logTrace;
		this.patterns = patterns;
	}

	// 인터페이스를 사용하여 JDK 기븐 프록시 구현
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 메서드 이름 필터
		String methodName = method.getName(); // 호출한 메서드 명

		// 호출한 메서드 이름이 특정 패턴에 매칭되지 않는다면 프록시를 적용하지 않는다.
		if(!PatternMatchUtils.simpleMatch(patterns, methodName)) {
			// 바로 리턴하면 안된다. 실제 타깃은 호출하고 로직을 종료한다.
			return method.invoke(target, args);
		}

		TraceStatus status = null;

		try {
			String simpleName = method.getDeclaringClass().getSimpleName();
			String name = method.getName();

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
