package hello.proxy.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

public class MultiAdvisorTest {
	@Test
	@DisplayName("여러 프록시")
	void multiAdvisorTest1() {
		// client -> proxy2(advisor2) -> proxy1(advisor1) -> target

		/**
		 * proxy1 생성
 		 */
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory1 = new ProxyFactory(service);

		// Advice1 어드바이스를 사용하고 모든 포인트컷에 적용하는 어드바이저 생성
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());

		// 프록시 팩토리(proxyFactory1)에 어드바이저 add
		proxyFactory1.addAdvisor(advisor1);

		// advisor1이 적용된 동적 프록시 proxy1 반환
		ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

		/**
		 * proxy2 생성
		 */
		ProxyFactory proxyFactory2 = new ProxyFactory(proxy1); // 기존 타깃(service)에 적용되는것이 아닌 프록시를 advice 해야함

		// Advice2 어드바이스를 사용하고 모든 포인트컷에 적용하는 어드바이저 생성
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

		// 프록시 팩토리(proxyFactory2)에 어드바이저 add
		proxyFactory2.addAdvisor(advisor2);

		// advisor2가 적용된 동적 프록시 proxy2 반환
		ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

		/**
		 * 프록시 호출 (클라이언트 -> 프록시 2 (어드바이저 2) -> 프록시 1 (어드바이저 1) -> 타깃 순으로 호출되어야함)
		 */
		proxy2.save();
	}

	@Test
	@DisplayName("하나의 프록시, 여러 어드바이저")
	void multiAdvisorTest2() {
		// client -> proxy -> advisor2 -> advisor1 -> target

		/**
		 * proxy 생성
		 */
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(service);

		// Advice2 어드바이스를 사용하고 모든 포인트컷에 적용하는 어드바이저 생성
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

		// 프록시 팩토리(proxyFactory2)에 어드바이저 add - 프록시를 호출하는 순서대로 add() 해야함
		proxyFactory.addAdvisor(advisor2);

		// Advice1 어드바이스를 사용하고 모든 포인트컷에 적용하는 어드바이저 생성
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());

		// 프록시 팩토리(proxyFactory)에 어드바이저 add
		proxyFactory.addAdvisor(advisor1);

		// advisor1이 적용된 동적 프록시 proxy 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		/**
		 * 프록시 호출 (클라이언트 -> 프록시 -> 어드바이저 2 -> 어드바이저 1 -> 타깃 순으로 호출되어야함)
		 */
		proxy.save();
	}

	@Slf4j
	static class Advice1 implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice1 호출");

			// invocation.proceed() : 프록시 팩토리에 주입한 타깃 클래스 정보를 통해 실제 로직 호출
			return invocation.proceed();
		}
	}

	@Slf4j
	static class Advice2 implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice2 호출");

			// invocation.proceed() : 프록시 팩토리에 주입한 타깃 클래스 정보를 통해 실제 로직 호출
			return invocation.proceed();
		}
	}
}
