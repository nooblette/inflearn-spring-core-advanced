package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TraceTemplate {
	private final LogTrace trace;

	// 각 Controller, Service, Repository 계층마다 반환 타입이 다르므로 제네릭을 활용한다.
	// 제네릭 타입 T : 구체적인 타입 지정을 클래스를 정의하는 시점이 아닌 객체를 생성하는 시점으로 미룬다.
	public <T> T execute(String message, TraceCallback<T> callback) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);

			// 변하는 로직(핵심 비즈니스 로직)을 콜백(callback)에게 위임한다. (추상화)
			T result = callback.call();

			trace.end(status);
			return result;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
