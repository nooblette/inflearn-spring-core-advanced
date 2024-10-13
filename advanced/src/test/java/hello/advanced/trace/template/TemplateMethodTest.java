package hello.advanced.trace.template;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateMethodTest {

	@Test
	void templateMethodV0() {
		logic1();
		logic2();
	}

	private void logic1() {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		log.info("비즈니스 로직1 실행");

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}

	private void logic2() {
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		log.info("비즈니스 로직2 실행");

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}
}
