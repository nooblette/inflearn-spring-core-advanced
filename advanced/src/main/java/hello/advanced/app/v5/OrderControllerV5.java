package hello.advanced.app.v5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@RestController
public class OrderControllerV5 {
	private final OrderServiceV5 orderService;
	private final TraceTemplate traceTemplate;

	// @Autowired // 생성자가 하나이므로 생략 가능
	public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
		this.orderService = orderService;
		this.traceTemplate = new TraceTemplate(logTrace);
	}

	@GetMapping("/v5/request")
	public String request(String itemId) {
		return traceTemplate.execute("OrderController.request()", new TraceCallback<String>() {
			@Override
			public String call() {
				// 핵심 비즈니스 로직(변하는 부분)
				orderService.orderItem(itemId);
				return "ok";
			}
		});
	}
}