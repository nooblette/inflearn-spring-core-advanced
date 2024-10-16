package hello.proxy.advisor;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(service);

		// 어드바이저 생성, Pointcut.TRUE = 항상 참
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

		// 프록시 팩토리(proxyFactory)에 어드바이저 add
		proxyFactory.addAdvisor(advisor);

		// 어드바이저가 적용된 동적 프록시 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	@Test
	@DisplayName("직접 만든 포인트컷")
	void advisorTest2() {
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(service);

		// 어드바이저 생성
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

		// 프록시 팩토리(proxyFactory)에 어드바이저 add
		proxyFactory.addAdvisor(advisor);

		// 어드바이저가 적용된 동적 프록시 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	static class MyPointcut implements Pointcut {

		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new MyMethodMatcher();
		}
	}

	static class MyMethodMatcher implements MethodMatcher {
		String matchedName = "save";

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			// matchedName에 해당하는 메서드만 어드바이스(Advice) 적용
			boolean result = method.getName().equals(matchedName);
			log.info("포인트컷 호출 method={}, targetClass={}", method, targetClass);
			log.info("포인트컷 결과 result={}", result);
			return result;
		}

		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			return false;
		}
	}

	@Test
	@DisplayName("스프링이 제공하는 포인트컷")
	void advisorTest3() {
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(service);

		// 스프링이 제공하는 포인트 컷 사용
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("save"); // 메서드 이름이 save인 경우 어드바이스 적용

		// 스프링에서 제공하는 포인트컷을 사용하여 어드바이저 생성
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

		// 프록시 팩토리(proxyFactory)에 어드바이저 add
		proxyFactory.addAdvisor(advisor);

		// 어드바이저가 적용된 동적 프록시 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}
}
