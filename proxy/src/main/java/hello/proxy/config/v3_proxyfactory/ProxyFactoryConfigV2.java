package hello.proxy.config.v3_proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {
	/**
	 * 1. 포인트컷과 어드바이스(LogTraceAdvice)를 사용하여 어드바이저(Advisor)를 구현한다.
	 * 2. 프록시 팩토리(ProxyFactory)를 통해 구현한 어드바이저(Advisor)를 사용하는 동적 프록시를 생성한다.
	 * 3. 타깃 클래스가 아닌 동적 프록시를 스프링 빈으로 등록한다.
	 */

	@Bean
	public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
		// 타깃 클래스 생성
		OrderControllerV2 orderController = new OrderControllerV2(orderServiceV2(logTrace));

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderController);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}", proxy.getClass());
		log.info("proxyFactory target{}", orderController.getClass());
		return proxy;
	}
	
	@Bean
	public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
		// 타깃 클래스 생성
		OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV2(logTrace));

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderService);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}", proxy.getClass());
		log.info("proxyFactory target{}", orderService.getClass());
		return proxy;
	}

	@Bean
	public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
		// 타깃 클래스 생성
		OrderRepositoryV2 orderRepository = new OrderRepositoryV2();

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}", proxy.getClass());
		log.info("proxyFactory target{}", orderRepository.getClass());
		return proxy;
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		// point cut
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");

		// advice 생성
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);

		// 어드바이저 생성 및 반환 (어드바이저는 하나의 포인트 컷과 하나의 어드바이스를 갖는다.)
		return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
	}
}
