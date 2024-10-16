package hello.proxy.config;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v2_dynamicproxy.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class DynamicProxyBasicConfig {

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		// orderServiceV1 프록시를 의존관계 주입한다.
		OrderControllerV1 orderController = new OrderControllerV1Impl(orderServiceV1(logTrace));

		OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(
			OrderControllerV1.class.getClassLoader(), // 인터페이스를 통해서 classLoader 정보를 얻는다.
			new Class[] {OrderControllerV1.class}, // 어떤 인터페이스를 기반으로 생성하는 프록시인지 명시한다. (배열로 넣어줘야함)
			new LogTraceBasicHandler(orderController, logTrace) // JDK 기반 프록시 생성(InvocationHandler를 구현하는 클래스)
		);

		return proxy;
	}

	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		// orderRepositoryV1 프록시를 의존관계 주입한다.
		OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

		OrderServiceV1 proxy = (OrderServiceV1)Proxy.newProxyInstance(
			OrderServiceV1.class.getClassLoader(), // 인터페이스를 통해서 classLoader 정보를 얻는다.
			new Class[] {OrderServiceV1.class}, // 어떤 인터페이스를 기반으로 생성하는 프록시인지 명시한다. (배열로 넣어줘야함)
			new LogTraceBasicHandler(orderService, logTrace) // JDK 기반 프록시 생성(InvocationHandler를 구현하는 클래스)
		);

		return proxy;
	}

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

		OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(
			OrderRepositoryV1.class.getClassLoader(), // 인터페이스를 통해서 classLoader 정보를 얻는다.
			new Class[] {OrderRepositoryV1.class}, // 어떤 인터페이스를 기반으로 생성하는 프록시인지 명시한다. (배열로 넣어줘야함)
			new LogTraceBasicHandler(orderRepository, logTrace) // JDK 기반 프록시 생성(InvocationHandler를 구현하는 클래스)
		);

		return proxy;
	}
}
