package hello.advanced.trace.strategy.code.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {
	public void execute(Callback callback) {
		long startTime = System.currentTimeMillis();

		// 변하는 부분, 핵심 비즈니스 로직을 콜백(callback)에게 위임한다.
		callback.call();

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime = {}", resultTime);
	}
}