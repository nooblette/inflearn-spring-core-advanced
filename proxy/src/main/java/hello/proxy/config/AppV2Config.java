package hello.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;

// V2 컨트룰러, 서비스, 리포지토리를 빈으로 수동 등록한다.
@Configuration
public class AppV2Config {

	@Bean // 스프링 빈으로 수동 등록
	public OrderControllerV2 orderControllerV2(){
		// 스프링 빈으로 수동 등록하면서 의존관계 주입도 이루어진다.
		return new OrderControllerV2(orderServiceV2());
	}

	@Bean
	public OrderServiceV2 orderServiceV2() {
		return new OrderServiceV2(orderRepositoryV2());
	}

	@Bean
	public OrderRepositoryV2 orderRepositoryV2() {
		return new OrderRepositoryV2();
	}
}
