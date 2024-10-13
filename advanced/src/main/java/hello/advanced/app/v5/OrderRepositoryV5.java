package hello.advanced.app.v5;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@Repository
public class OrderRepositoryV5 {
	private final TraceTemplate traceTemplate;

	// @Autowired // 생성자가 하나이므로 생략 가능
	public OrderRepositoryV5(LogTrace logTrace) {
		this.traceTemplate = new TraceTemplate(logTrace);
	}

	public void save(String itemId) {
		traceTemplate.execute("OrderRepository.save()", () -> {
			// 저장 로직
			// 핵심 비즈니스 로직(변하는 부분)
			if (itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생!");
			}
			sleep(1000);
			return null;
		});
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}