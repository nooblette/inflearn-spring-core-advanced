package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {
	// 프록시 객체는 실제 타깃 클래스에 의존하고 호출한다.
	private final OrderControllerV2 target;
	private final LogTrace logTrace;

	public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
		// 자바 문법상 자식 클래스는 부모 클래스의 생성자를 항상 가장 먼저 호출해야한다.
		// 생략하는 경우 기본 생성자(super())를 호출하게 된다.
		super(null);
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public String request(String itemId) {
		TraceStatus status = logTrace.begin("OrderController.request");
		try {
			// 타깃 클래스 호출
			String result = target.request(itemId);

			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);

			// 기존 동작에 영향을 주면 안되므로 예외는 다시 던진다.
			throw e;
		}
	}

	@Override
	public String noLog() {
		// noLog는 로그를 찍지 않는다. (요구사항)
		return target.noLog();
	}
}
