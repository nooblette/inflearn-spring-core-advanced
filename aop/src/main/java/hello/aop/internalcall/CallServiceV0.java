package hello.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV0 {
	public void external() {
		log.info("call external");
		internal(); // 내부 메서드 호출(this 키워드는 생략 가능, this : 자기 자신 인스턴스를 가리킨다.)
	}

	public void internal() {
		log.info("call internal");
	}
}
