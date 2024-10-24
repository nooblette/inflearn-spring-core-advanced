package hello.aop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV5Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
// @Import(AspectV1.class) // AspectV1을 스프링 빈으로 등록한다.
// @Import(AspectV2.class) // AspectV2을 스프링 빈으로 등록한다.
// @Import(AspectV3.class) // AspectV3을 스프링 빈으로 등록한다.
// @Import(AspectV4Pointcut.class) // AspectV4Pointcut을 스프링 빈으로 등록한다.
@Import({AspectV5Order.LogAspect.class, AspectV5Order.TransactionAspect.class}) // 2개의 Aspect를 각각 스프링 빈으로 등록한다.
public class AopTest {
	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	@Test
	void aopInfo() {
		log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
		log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
	}

	@Test
	void success() {
		orderService.orderItem("itemA");
	}

	@Test
	void exception() {
		Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
			.isInstanceOf(IllegalStateException.class);
	}
}
