package hello.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 구조를 변경(클래스 분리)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {
	private final InternalService internalService;

	public void external() {
		log.info("call external");
		internalService.internal();
	}
}
