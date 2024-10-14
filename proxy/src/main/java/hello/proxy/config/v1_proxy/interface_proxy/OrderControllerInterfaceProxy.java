package hello.proxy.config.v1_proxy.interface_proxy;

import org.springframework.web.bind.annotation.RestController;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

	// 프록시 객체는 실제 타깃 클래스에 의존하고 호출한다.
	private final OrderControllerV1 target;
	private final LogTrace logTrace;

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
