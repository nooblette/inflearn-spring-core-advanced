package hello.advanced.app.v1;

import org.springframework.stereotype.Service;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;

@Service // OrderServiceV1 클래스를 Component Scan 대상에 포함, 자동으로 스프링 빈으로 등록, @Componenet 어노테이션을 포함하고 있다.
@RequiredArgsConstructor // final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class OrderServiceV1 {
	private final OrderRepositoryV1 orderRepositoryV1;
	private final HelloTraceV1 trace;

	public void orderItem(String itemId) {
		TraceStatus status = trace.begin("OrderServiceV1.orderItem");
		try {
			// 상품 저장
			orderRepositoryV1.save(itemId);

			trace.end(status);
		} catch (Exception e) {
			// 예외 발생 로그를 위함
			trace.exception(status, e);

			// 로그 추적기는 애플리케이션 흐름에 영향을 주면 안됨, 예외를 다시 던진다.
			throw e;
		}
	}
}
