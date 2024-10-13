package hello.advanced.trace.strategy;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.template.Callback;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateCallbackTest {

	/**
	 * 템플릿 콜백 패턴 - 익명 내부 클래스
	 */
	@Test
	void callbackV1() {
		TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

		// 코드 조각(익명 내부 클래스)을 execute 메서드의 매개변수로 넘긴다.
		timeLogTemplate.execute(new Callback() {
			@Override
			public void call() {
				log.info("비즈니스 로직 1 실행");
			}
		});

		timeLogTemplate.execute(new Callback() {
			@Override
			public void call() {
				log.info("비즈니스 로직 2 실행");
			}
		});
	}

	/**
	 * 템플릿 콜백 패턴 - 람다
	 */
	@Test
	void callbackV2() {
		TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

		// 코드 조각(익명 내부 클래스)을 execute 메서드의 매개변수로 넘긴다.
		timeLogTemplate.execute(() -> log.info("비즈니스 로직 1 실행"));

		timeLogTemplate.execute(() -> log.info("비즈니스 로직 2 실행"));
	}
}
