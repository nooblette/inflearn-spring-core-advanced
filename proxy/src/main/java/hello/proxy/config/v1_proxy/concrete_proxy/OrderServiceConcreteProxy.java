package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
	private final OrderServiceV2 target;
	private final LogTrace logTrace;

	public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
		// 자바 문법상 자식 클래스는 부모 클래스의 생성자를 항상 가장 먼저 호출해야한다.
		// 생략하는 경우 기본 생성자(super())를 호출하게 된다.
		super(null);
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public void orderItem(String itemId) {
		TraceStatus status = logTrace.begin("OrderService.orderItem");
		try {
			// 타깃 클래스 호출
			target.orderItem(itemId);

			logTrace.end(status);
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}
}
