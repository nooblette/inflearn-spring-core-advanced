package hello.advanced.app.v2;

import org.springframework.stereotype.Service;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

@Service // OrderServiceV2 클래스를 Component Scan 대상에 포함, 자동으로 스프링 빈으로 등록, @Componenet 어노테이션을 포함하고 있다.
@RequiredArgsConstructor // final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class OrderServiceV2 {
	private final OrderRepositoryV2 orderRepositoryV1;
	private final HelloTraceV2 trace;

	public void orderItem(TraceId traceId, String itemId) {
		TraceStatus status = trace.beginSync(traceId, "OrderServiceV2.orderItem");

		try {
			// 상품 저장
			orderRepositoryV1.save(status.getTraceId(), itemId);

			trace.end(status);
		} catch (Exception e) {
			// 예외 발생 로그를 위함
			trace.exception(status, e);

			// 로그 추적기는 애플리케이션 흐름에 영향을 주면 안됨, 예외를 다시 던진다.
			throw e;
		}
	}
}
