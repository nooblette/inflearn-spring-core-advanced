package hello.advanced.trace.strategy;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.strategy.ContextV2;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2Test {
	/**
	 * 전략 패턴 적용 - 전략을 파라미터로 전달한다.
	 */
	@Test
	void strategyV1() {
		ContextV2 contextV2 = new ContextV2();
		contextV2.execute(new StrategyLogic1());
		contextV2.execute(new StrategyLogic2());
	}

	/**
	 * 전략 패턴 - 익명 내부 클래스
	 */
	@Test
	void strategyV2() {
		ContextV2 contextV2 = new ContextV2();
		contextV2.execute(new Strategy(){
			@Override
			public void call() {
				log.info("비즈니스 로직 1 실행");
			}
		});

		contextV2.execute(new Strategy(){
			@Override
			public void call() {
				log.info("비즈니스 로직 2 실행");
			}
		});
	}

	/**
	 * 전략 패턴 - 람다
	 */
	@Test
	void strategyV3() {
		ContextV2 contextV2 = new ContextV2();
		contextV2.execute(() -> log.info("비즈니스 로직 1 실행"));

		contextV2.execute(() -> log.info("비즈니스 로직 2 실행"));
	}
}
