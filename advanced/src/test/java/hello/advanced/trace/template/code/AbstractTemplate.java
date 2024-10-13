package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {
	public void execute() {
		// 추상 템플릿(AbstractTemplate)은 변하는 부분은 모두 추상 클래스에 구현한다.
		long startTime = System.currentTimeMillis();

		// 비즈니스 로직 실행
		// 변하는 부분(핵심 비즈니스 로직)을 상속을 통해서 해결한다.
		call();

		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime; // 핵심 로직(비즈니스 로직)의 실행 시간을 측정하는 부가 기능
		log.info("resultTime = {}", resultTime);
	}

	protected abstract void call();
}
