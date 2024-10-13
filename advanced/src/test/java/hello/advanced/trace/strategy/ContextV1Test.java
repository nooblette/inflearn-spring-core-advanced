package hello.advanced.trace.strategy;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1Test {

	@Test
	void strategyV0() {
		logic1();
		logic2();
	}

	private void logic1() {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		log.info("비즈니스 로직1 실행"); // 변하는 부분

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}

	private void logic2() {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		log.info("비즈니스 로직2 실행"); // 변하는 부분

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}

	/**
	 * 전략 패턴 사용
	 */
	@Test
	void strategyV1() {
		StrategyLogic1 strategyLogic1 = new StrategyLogic1();
		ContextV1 contextV1 = new ContextV1(strategyLogic1); // 전략 주입
		contextV1.execute();

		StrategyLogic2 strategyLogic2 = new StrategyLogic2();
		ContextV1 contextV2 = new ContextV1(strategyLogic2); // 전략 주입
		contextV2.execute();
	}

	/**
	 * 전략 패턴에 익명 내부 클래스 적용
	 */
	@Test
	void strategyV2() {
		Strategy strategyLogic1 = new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행"); // 변하는 부분
			}
		};
		ContextV1 contextV1 = new ContextV1(strategyLogic1);
		log.info("strategyLogic1={}", strategyLogic1.getClass());
		contextV1.execute();

		Strategy strategyLogic2 = new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행"); // 변하는 부분
			}
		};
		ContextV1 contextV2 = new ContextV1(strategyLogic2);
		log.info("strategyLogic2={}", strategyLogic2.getClass());
		contextV2.execute();
	}

	/**
	 * 전략 패턴에 익명 내부 클래스 적용 - 변수 선언 없이 바로 전략 인터페이스의 구현체 주입
	 */
	@Test
	void strategyV3() {
		ContextV1 contextV1 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행"); // 변하는 부분
			}
		});
		contextV1.execute();

		ContextV1 contextV2 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행"); // 변하는 부분
			}
		});
		contextV2.execute();
	}

	/**
	 * 익명 내부 클래스와 람다 적용
	 */
	@Test
	void strategyV4() {
		ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
		contextV1.execute();

		ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
		contextV2.execute();
	}
}
