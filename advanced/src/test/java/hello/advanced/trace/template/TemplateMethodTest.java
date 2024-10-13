package hello.advanced.trace.template;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
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

	/**
	 * 템플릿 메서드 패턴 적용
	 */
	@Test
	void templateMethodV1() {
		AbstractTemplate template1 = new SubClassLogic1();
		template1.execute();

		AbstractTemplate template2 = new SubClassLogic2();
		template2.execute();
	}

	/**
	 * 익명 내부 클래스로 템플릿 메서드 패턴 적용
	 */
	@Test
	void templateMethodV2() {
		// 익명 내부 클래스를 적용하여 template1 인스턴스를 생성하면서 동시에 AbstractTemplate를 상속받는 클래스를 구현할 수 있다.
		// 쉽게 말해서 객체 인스턴스를 생성하면서 추상 템플릿을 상속받는 클래스를 만드면서 변하는 로직(핵심 비즈니스 로직)을 작성하는 것이다.
		AbstractTemplate template1 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직1 실행");
			}
		};

		// 익명 내부 클래스의 이름 출력
		log.info("클래스 이름1={}", template1.getClass());
		template1.execute();

		AbstractTemplate template2 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직2 실행");
			}
		};
		// 익명 내부 클래스의 이름 출력
		log.info("클래스 이름2={}", template2.getClass());
		template2.execute();
	}
}
