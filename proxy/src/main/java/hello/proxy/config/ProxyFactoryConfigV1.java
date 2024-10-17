package hello.proxy.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {
	/**
	 * 1. 포인트컷과 어드바이스(LogTraceAdvice)를 사용하여 어드바이저(Advisor)를 구현한다.
	 * 2. 프록시 팩토리(ProxyFactory)를 통해 구현한 어드바이저(Advisor)를 사용하는 동적 프록시를 생성한다.
	 * 3. 타깃 클래스가 아닌 동적 프록시를 스프링 빈으로 등록한다.
	 */

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		// 타깃 클래스 생성 - orderControllerV1 orderServiceV1 프록시에 의존한다.
		OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderControllerV1);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderControllerV1 proxy = (OrderControllerV1) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}, target{}", proxy.getClass(), orderControllerV1.getClass());
		return proxy;
	}
	
	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		// 타깃 클래스 생성 - orderServiceV1는 orderRepositoryV1 프록시에 의존한다.
		OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderServiceV1);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderServiceV1 proxy = (OrderServiceV1) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}, target{}", proxy.getClass(), orderServiceV1.getClass());
		return proxy;
	}

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		// 타깃 클래스 생성
		OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

		// 프록시 팩토리(ProxyFactory)를 통해 프록시 생성
		ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
		proxyFactory.addAdvisor(getAdvisor(logTrace)); // 어드바이저 한개 적용(한개의 프록시에 n개의 어드바이저를 설정한다.)

		// 생성된 동적 프록시를 빈으로 등록
		OrderRepositoryV1 proxy = (OrderRepositoryV1) proxyFactory.getProxy();
		log.info("proxyFactory proxy={}, target{}", proxy.getClass(), orderRepository.getClass());
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
