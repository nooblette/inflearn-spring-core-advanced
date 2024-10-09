package hello.advanced.app.v2;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

@Repository // OrderRepositoryV2 클래스를 Component Scan 대상에 포함, 자동으로 스프링 빈으로 등록, @Componenet 어노테이션을 포함하고 있다.
@RequiredArgsConstructor
public class OrderRepositoryV2 {
	private final HelloTraceV2 trace;

	public void save(TraceId traceId, String itemId) {
		TraceStatus status = trace.beginSync(traceId, "OrderRepositoryV2.save");
		try {
			// 상품 저장
			if(itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생!");
			}

			sleep(1000); // 상품 저장하는데 1000ms(= 1sec)가 걸린다고 가정(1000ms간 대기)

			trace.end(status);
		} catch (Exception e) {
			// 예외 발생 로그를 위함
			trace.exception(status, e);

			// 로그 추적기는 애플리케이션 흐름에 영향을 주면 안됨, 예외를 다시 던진다.
			throw e;
		}
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
