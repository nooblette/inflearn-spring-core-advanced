package hello.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;

// V1 컨트룰러, 서비스, 리포지토리를 빈으로 수동 등록한다.
@Configuration
public class AppV1Config {

	@Bean // 스프링 빈으로 수동 등록
	public OrderControllerV1 orderControllerV1(){
		// 스프링 빈으로 수동 등록하면서 의존관계 주입도 이루어진다.
		return new OrderControllerV1Impl(orderServiceV1());
	}

	@Bean
	public OrderServiceV1 orderServiceV1() {
		return new OrderServiceV1Impl(orderRepositoryV1());
	}

	@Bean
	public OrderRepositoryV1 orderRepositoryV1() {
		return new OrderRepositoryV1Impl();
	}
}
