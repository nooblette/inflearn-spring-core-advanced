package hello.proxy.cglib.code;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

	private final Object target; // 항상 프록시는 실제로 호출할 타깃 클래스가 필요하다.

	public TimeMethodInterceptor(Object target) {
		this.target = target;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		log.info("TimeMethodInterceptor 실행");
		long startTime = System.currentTimeMillis();

		Object result = methodProxy.invoke(target, args); // methodProxy를 invoke 하는게 더 성능 최적화가 이루어져있음
		// methodProxy.invoke(target, args); // JDK 호출 방식

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeMethodInterceptor 종료 resultTime = {}", resultTime);
		return result;
	}
}
