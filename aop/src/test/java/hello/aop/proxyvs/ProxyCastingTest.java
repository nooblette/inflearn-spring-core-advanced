package hello.aop.proxyvs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyCastingTest {
	@Test
	void jdkProxy() {
		// MemberServiceImpl는 구체클래스와 인터페이스가 모두 있다.
		MemberServiceImpl target = new MemberServiceImpl();

		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시 기반으로 프록시 생성

		// JDK 프록시를 인터페이스 캐스팅 - 성공 (JDK 동적 프록시는 인터페이스 기반으로 프록시를 생성하므로)
		Object memberServiceProxy = (MemberService) proxyFactory.getProxy();

		// JDK 프록시를 구체클래스 캐스팅 - 실패 (ClassCastException 발생)
		// JDK 동적 프록시는 인터페이스 기반으로 프록시를 생성한다. MemberServiceImpl과 아무런 관계가 없다.
		Assertions.assertThrows(ClassCastException.class, () -> {
			MemberServiceImpl castingMemberService = (MemberServiceImpl)proxyFactory.getProxy();
		});
	}

	@Test
	void cglibProxy() {
		// MemberServiceImpl는 구체클래스와 인터페이스가 모두 있다.
		MemberServiceImpl target = new MemberServiceImpl();

		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true); // CGLIB 동적 프록시 기반으로 프록시 생성

		// CGLIB 프록시를 인터페이스 캐스팅 - 성공 (JDK 동적 프록시는 인터페이스 기반으로 프록시를 생성하므로)
		Object memberServiceProxy = (MemberService) proxyFactory.getProxy();

		// CGLIB 프록시를 구체클래스 캐스팅 - 성공(MemberServiceImpl를 기반으로 프록시르 생성하므로)
		MemberServiceImpl castingMemberService = (MemberServiceImpl)proxyFactory.getProxy();
	}
}
