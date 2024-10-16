package hello.proxy.common.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
	// Advice는 타킷 클래스를 직접 넣어주지 않는다. (프록시 팩터리에서 들고 있다.)

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("TimeAdvice 실행");
		long startTime = System.currentTimeMillis();

		// MethodInvocation 클래스의 proceed()를 호출하면 내부에서 알아서 타깃 클래스를 찾고 호출한다.
		Object result = invocation.proceed();

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeAdvice 종료 resultTime = {}", resultTime);
		return result;
	}
}
