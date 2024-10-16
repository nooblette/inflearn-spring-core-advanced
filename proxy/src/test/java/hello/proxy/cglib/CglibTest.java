package hello.proxy.cglib;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CglibTest {

	@Test
	void cglib() {
		// 인터페이스가 없는 구체 클래스에 대해 cglib로 동적 프록시를 생성한다.
		ConcreteService target = new ConcreteService();

		// 구체 클래스(ConcreteService)를 상속받는 프록시를 만들어야한다.
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ConcreteService.class); // 부모 클래스를 ConcreteService 타입으로 지정
		enhancer.setCallback(new TimeMethodInterceptor(target)); // 프록시를 지정

		// ConcreteService 구체 클래스를 상속받는 동적 프록시 객체 생성
		ConcreteService proxy = (ConcreteService) enhancer.create();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		// 프록시의 메서드 호출
		proxy.call();
	}
}
