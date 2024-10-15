package hello.proxy.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;

public class ConcreteProxyTest {

	@Test
	void noProxy(){
		ConcreteLogic concreteLogic = new ConcreteLogic();

		// ConcreteClient 클라이언트는 ConcreteLogic 클래스를 의존관계 주입 받는다.
		ConcreteClient concreteClient = new ConcreteClient(concreteLogic);

		// 코드 실행
		concreteClient.execute();
	}
}
