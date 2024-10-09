package hello.advanced.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody, OrderControllerV1 클래스도 Component Scan 대상에 포함됨
@RequiredArgsConstructor
public class OrderControllerV1 {
	private final OrderServiceV1 orderServiceV1;
	private final HelloTraceV1 trace;

	@GetMapping("/v1/request")
	public String request(String itemId) {
		TraceStatus status = trace.begin("OrderControllerV1.request");
		try {
			orderServiceV1.orderItem(itemId);
			trace.end(status);
			return "ok"; // @ResponseBody에 의해 문자를 API 응답에 반환
		} catch (Exception e) {
			// 예외 발생 로그를 위함
			trace.exception(status, e);

			// 로그 추적기는 애플리케이션 흐름에 영향을 주면 안됨, 예외를 다시 던진다.
			throw e;
		}
	}
}
