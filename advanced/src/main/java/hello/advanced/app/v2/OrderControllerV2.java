package hello.advanced.app.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody, OrderControllerV2 클래스도 Component Scan 대상에 포함됨
@RequiredArgsConstructor
public class OrderControllerV2 {
	private final OrderServiceV2 orderService;
	private final HelloTraceV2 trace;

	@GetMapping("/v2/request")
	public String request(String itemId) {
		TraceStatus status = trace.begin("OrderControllerV2.request");
		try {
			orderService.orderItem(status.getTraceId(), itemId);
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
