package hello.proxy.config.v1_proxy.interface_proxy;

import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

	// 프록시 객체는 실제 타깃 클래스에 의존하고 호출한다.
	private final OrderRepositoryV1 target;
	private final LogTrace logTrace;

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
