package hello.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 구조를 변경(클래스 분리)
 */
@Slf4j
@Component
public class InternalService {

	public void internal() {
		log.info("call internal");
	}
}
