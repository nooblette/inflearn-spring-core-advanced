package hello.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;

// V2 컨트룰러, 서비스, 리포지토리를 빈으로 수동 등록한다.
@Configuration
public class InterfaceProxyConfig {
	@Bean
	public OrderControllerV1 orderController(LogTrace logTrace){
		// 실제 컨트룰러 객체(OrderControllerV1Impl)는 프록시 서비스 객체(orderService()에서 빈으로 등록)에 의존한다.
		OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));

		// 프록시 객체(OrderControllerInterfaceProxy)는 실제 타깃 클래스(controllerImpl)에 의존하고 있고, 프록시 객체가 스프링 빈으로 등록된다.
		return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
	}

	@Bean
	public OrderServiceV1 orderService(LogTrace logTrace) {
		OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));

		return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
	}

	@Bean
	public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
		OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
		return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
	}
}
