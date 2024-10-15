package hello.proxy.jdkdynamic;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import hello.proxy.jdkdynamic.code.AImpl;
import hello.proxy.jdkdynamic.code.AInterface;
import hello.proxy.jdkdynamic.code.BImpl;
import hello.proxy.jdkdynamic.code.BInterface;
import hello.proxy.jdkdynamic.code.TimeInvocationHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdkDynamicProxyTest {
	@Test
	void dynamicA() {
		AInterface target = new AImpl();

		TimeInvocationHandler handler = new TimeInvocationHandler(target);

		// AInterface 인터페이스를 구현하는 동적 프록시 객체 proxy 생성
		AInterface proxy = (AInterface) Proxy.newProxyInstance(
			AInterface.class.getClassLoader(), // 클래스 로더
			new Class[] {AInterface.class}, // 어떤 인터페이스를 기반으로 프록시를 생성할지 지정
			handler); // 프록시가 호출할 로직

		// proxy 객체에서 call() 호출
		proxy.call();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}

	@Test
	void dynamicB() {
		BInterface target = new BImpl();

		TimeInvocationHandler handler = new TimeInvocationHandler(target);

		// AInterface 인터페이스를 구현하는 동적 프록시 객체 proxy 생성
		BInterface proxy = (BInterface) Proxy.newProxyInstance(
			BInterface.class.getClassLoader(), // 클래스 로더
			new Class[] {BInterface.class}, // 어떤 인터페이스를 기반으로 프록시를 생성할지 지정
			handler); // 프록시가 호출할 로직

		// proxy 객체에서 call() 호출
		proxy.call();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}
}
