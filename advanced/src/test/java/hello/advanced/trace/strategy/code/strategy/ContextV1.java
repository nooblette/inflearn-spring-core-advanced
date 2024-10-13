package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식, 변하지 않는 부분을 담당한다.
 */
@Slf4j
public class ContextV1 {
	// 변하는 부분(핵심 비즈니스 로직)인 전략(Strategy 인터페이스)를 생성자를 통해서 주입받는다.
	private final Strategy strategy;

	public ContextV1(Strategy strategy) {
		this.strategy = strategy;
	}

	public void execute() {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		strategy.call(); // 변하는 부분, 핵심 비즈니스 로직을 전략(strategy)에게 위임한다.

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}
}
