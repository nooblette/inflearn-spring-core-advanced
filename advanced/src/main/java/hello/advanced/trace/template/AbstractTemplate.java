package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {
	private final LogTrace trace;

	// 각 Controller, Service, Repository 계층마다 반환 타입이 다르므로 제네릭을 활용한다.
	// 제네릭 타입 T : 구체적인 타입 지정을 클래스를 정의하는 시점이 아닌 객체를 생성하는 시점으로 미룬다.
	public T execute(String message) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			
			// 로직 호출 (추상화)
			T result = call();
			trace.end(status);
			return result;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}

	protected abstract T call();
}
