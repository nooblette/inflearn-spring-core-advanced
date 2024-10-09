package hello.advanced.trace.hellotrace;

import org.springframework.stereotype.Component;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// 해당 클래스는 싱글톤 객체로 스프링 빈으로 등록해서 사용한다.
public class HelloTraceV1 {
	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";

	// 메서드를 시작할떄 로그를 출력
	public TraceStatus begin(String message) {
		// e.g. [796bccd9] OrderController.request()
		TraceId traceId = new TraceId();
		Long startTimeMs = System.currentTimeMillis();

		// 로그 출력
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

		return new TraceStatus(traceId, startTimeMs, message);
	}

	// 메서드가 정상적으로 종료될때 로그를 출력, begin을 호출할때 생성한 TraceStatus 인스턴스를 매개변수로 받는다.
	public void end(TraceStatus status) {
		// e.g. [796bccd9] OrderController.request() time=1016ms
		complete(status, null);
	}

	// 메서드에서 예외가 발생했을때 로그 출력
	public void exception(TraceStatus status, Exception e) {
		// e.g. [b7119f27] |<X-OrderService.orderItem() time=10ms
		complete(status, e);
	}

	private void complete(TraceStatus status, Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs = stopTimeMs - status.getStartTimeMs();
		TraceId traceId = status.getTraceId();
		if (e == null) {
			log.info("[{}] {}{} time={}ms", traceId.getId(),
				addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
				resultTimeMs);
		} else {
			log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
				addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
				e.toString());
		}
	}

	private static String addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append( (i == level - 1) ? "|" + prefix : "|   ");
		}
		return sb.toString();
	}
}
