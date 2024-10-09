package hello.advanced.trace;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TraceId {
	private String id;
	private int level;

	public TraceId() {
		this.id = createId();
		this.level = 0;
	}

	private TraceId(String id, int level) {
		this.id = id;
		this.level = level;
	}

	private String createId() {
		// 생성된 UUID 중 앞 8자리만 사용한다.
		return UUID.randomUUID().toString().substring(0, 8);
	}

	public TraceId createNextId() {
		return new TraceId(id, level + 1);
	}

	public TraceId createPreviousId() {
		return new TraceId(id, level - 1);
	}

	public boolean isFirstLevel() {
		return level == 0;
	}
}