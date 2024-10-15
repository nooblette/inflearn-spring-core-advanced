package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {
	private final OrderRepositoryV2 target;
	private final LogTrace logTrace;

	public OrderRepositoryConcreteProxy(OrderRepositoryV2 target, LogTrace logTrace) {
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public void save(String itemId) {
		TraceStatus status = logTrace.begin("orderRepository.request");
		try {
			// 타깃 클래스 호출
			target.save(itemId);

			logTrace.end(status);
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}
}
