package hello.aop.internalcall;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ObjectProvider(Provider), ApplicationContext를 사용해서 지연(LAZY) 조회
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {
	// 지연 조회를 위해 스프링 컨테이너를 조회한다.
	// private final ApplicationContext applicationContext;

	// 스프링 컨테이너를 그대로 가져오는건 너무 방대하다. -> ObjectProvider로 특정 빈만 조회하도록 한다.
	private final ObjectProvider<CallServiceV2> objectProvider;

	public void external() {
		log.info("call external");

		// callServiceV2 빈을 지연 조회한다.
		CallServiceV2 callServiceV2 = objectProvider.getObject(); // 스프링 컨테이너에서 지정한 특정 빈만 지연 조회한다.
		callServiceV2.internal();
	}

	public void internal() {
		log.info("call internal");
	}
}
