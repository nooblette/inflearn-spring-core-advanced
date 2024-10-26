package hello.aop.internalcall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV1 {
	// 자기 자신에 의존한다.
	private CallServiceV1 callServiceV1;

	// 자기 자신을 생성자를 통해 의존관계 주입받으면 순환 참조 문제가 발생한다.
	// 따라서 생성자로 의존관계 주입받을 수 없다. (자기 자신을 주입받아야하는데 자기 자신이 아직 생성되지 않음)
	// 따라서 수정자 주입으로 의존관계를 주입해야한다.
	// 스프링 2.6버전부터는 기본적으로 순환 참조를 금지한다. (수정자 주입도 불가)
	// 따라서 application.properties에서 spring.main.allow-circular-references=true로 설정한다.
	@Autowired
	public void setCallServiceV1(CallServiceV1 callServiceV1) {
		log.info("callServiceV1 setter={}", callServiceV1.getClass());
		this.callServiceV1 = callServiceV1;
	}


	public void external() {
		log.info("call external");
		callServiceV1.internal(); // 자기 자신의 메서드 호출
	}

	public void internal() {
		log.info("call internal");
	}
}
