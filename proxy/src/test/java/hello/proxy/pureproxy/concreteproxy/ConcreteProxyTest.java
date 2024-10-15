package hello.proxy.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;

public class ConcreteProxyTest {

	@Test
	void noProxy(){
		ConcreteLogic concreteLogic = new ConcreteLogic();

		// ConcreteClient 클라이언트는 ConcreteLogic 클래스를 의존관계 주입 받는다.
		ConcreteClient concreteClient = new ConcreteClient(concreteLogic);

		// 코드 실행
		concreteClient.execute();
	}

	@Test
	void addProxy() {
		ConcreteLogic concreteLogic = new ConcreteLogic();

		// TimeProxy 클래스 : 실제 대상 타깃 클래스 ConcreteLogic 클래스를 의존관계 주입 받아 호출한다.
		ConcreteLogic timeProxy = new TimeProxy(concreteLogic);

		// ConcreteClient 클라이언트는 프록시 객체인 TimeProxy 클래스를 의존관계 주입 받는다.
		ConcreteClient concreteClient = new ConcreteClient(timeProxy);
		concreteClient.execute();
	}
}
