package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";

	private TraceId traceIdHolder; // trace Id 동기화 목적, 동시성 이슈가 발생할 수 있음

	@Override
	public TraceStatus begin(String message) {
		syncTraceId();
		TraceId traceId = traceIdHolder;
		Long startTimeMs = System.currentTimeMillis();

		// 로그 출력
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

		return new TraceStatus(traceId, startTimeMs, message);
	}

	// syncTraceId를 호출하고나면 traceIdHolder에 항상 값이 있음이 보장된다.
	private void syncTraceId() {
		// 첫번째 레벨(가장 먼저 호출한 메서드)인 경우
		if(traceIdHolder == null) {
			traceIdHolder = new TraceId();
		} else {
			// 첫번째 레벨이 아닌 중간 레벨(도중에 호출되는 메서드)인 경우
			traceIdHolder = traceIdHolder.createNextId();
		}
	}

	@Override
	public void end(TraceStatus status) {
		complete(status, null);
	}

	@Override
	public void exception(TraceStatus status, Exception e) {
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

		releaseTraceId();
	}

	private void releaseTraceId() {
		// 첫번째 레벨(가장 먼저 호출한 메서드)까지 도달한 경우
		if(traceIdHolder.isFirstLevel()) {
			traceIdHolder = null;
		} else {
			// 첫번째 레벨이 아닌 중간 레벨(도중에 호출되는 메서드)인 경우
			traceIdHolder = traceIdHolder.createPreviousId();
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