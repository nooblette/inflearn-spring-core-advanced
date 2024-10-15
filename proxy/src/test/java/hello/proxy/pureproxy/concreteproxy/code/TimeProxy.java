package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {
	private ConcreteLogic concreteLogic;

	// 타깃 클래스(concreteLogic)를 의존관계 주입 받는다.
	public TimeProxy(ConcreteLogic concreteLogic) {
		this.concreteLogic = concreteLogic;
	}

	// 상속을 통해 다형성을 활용
	@Override
	public String operation() {
		// TimeProxy 프록시에 시간을 측정하는 부가 기능을 추가 (데코레이터 패턴)
		log.info("TimeDecorator 실행");
		long startTime = System.currentTimeMillis();

		// 타깃 클래스를 호출한다.
		String result = concreteLogic.operation();
		long endTime = System.currentTimeMillis();

		long resultTime = endTime - startTime;
		log.info("TimeDecorator 종료 resultTime={} ms", resultTime);
		return result;
	}
}
