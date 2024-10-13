package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 전달 받는 방식
 */
@Slf4j
public class ContextV2 {
	public void execute(Strategy strategy) {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		strategy.call(); // 변하는 부분, 핵심 비즈니스 로직을 전략(strategy)에게 위임한다.

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}
}
