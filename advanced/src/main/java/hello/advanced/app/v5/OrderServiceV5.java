package hello.advanced.app.v5;

import org.springframework.stereotype.Service;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@Service
public class OrderServiceV5 {
	private final OrderRepositoryV5 orderRepository;
	private final TraceTemplate traceTemplate;

	// @Autowired // 생성자가 하나이므로 생략 가능
	public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace logTrace) {
		this.orderRepository = orderRepository;
		this.traceTemplate = new TraceTemplate(logTrace);
	}

	public void orderItem(String itemId) {
		traceTemplate.execute("OrderService.orderItem()", (TraceCallback<Void>)() -> {
			// 핵심 비즈니스 로직(변하는 부분)
			orderRepository.save(itemId);
			return null;
		});
	}
}