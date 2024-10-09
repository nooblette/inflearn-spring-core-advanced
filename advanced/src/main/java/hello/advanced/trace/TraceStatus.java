package hello.advanced.trace;

// 로그의 상태 정보를 나타내는 클래스
public class TraceStatus {
	private TraceId traceId;
	private Long startTimeMs; // 시작 시간을 통해 메서드의 수행 시간을 체크할 수 있다.
	private String message;  // 해당 로그가 시작되는 메서드명을 기록한다.

	public TraceStatus(TraceId traceId, Long startTimeNo, String message) {
		this.traceId = traceId;
		this.startTimeMs = startTimeNo;
		this.message = message;
	}

	public TraceId getTraceId() {
		return traceId;
	}

	public Long getStartTimeMs() {
		return startTimeMs;
	}

	public String getMessage() {
		return message;
	}
}
