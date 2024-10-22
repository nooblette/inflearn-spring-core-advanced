package hello.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class ConcreteProxyConfig {

	@Bean
	public OrderControllerV2 orderController(LogTrace logTrace) {
		OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
		return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
	}

	@Bean
	public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
		// OrderServiceConcreteProxy 프록시 객체는 실제 타깃 클래스 OrderServiceV2에 의존한다.
		// 또한 OrderServiceV2는 OrderRepositoryConcreteProxy 프록시 객체에 의존한다.
		OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));

		// OrderServiceConcreteProxy 프록시 객체가 빈으로 등록된다.
		return new OrderServiceConcreteProxy(orderServiceImpl, logTrace);
	}

	@Bean
	public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
		// OrderRepositoryConcreteProxy 프록시 클래스는 실제 타깃 클래스 OrderRepositoryV2에 의존한다.
		OrderRepositoryV2 orderRepositoryImpl = new OrderRepositoryV2();

		// OrderRepositoryConcreteProxy 프록시 객체가 빈으로 등록된다.
		return new OrderRepositoryConcreteProxy(orderRepositoryImpl, logTrace);
	}


}
