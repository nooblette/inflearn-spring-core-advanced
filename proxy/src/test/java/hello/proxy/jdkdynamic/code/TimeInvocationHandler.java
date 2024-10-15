package hello.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

	// 프록시가 호출할 대상 (타깃 클래스) 주입
	private final Object target;

	public TimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("TimeProxy 실행");
		long startTime = System.currentTimeMillis();

		// 실제 타깃 클래스(target)의 method 메서드를 호출
		Object result = method.invoke(target, args);

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeProxy 종료 resultTime = {}", resultTime);
		return result;
	}
}
