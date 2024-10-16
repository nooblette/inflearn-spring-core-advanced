package hello.proxy.proxyfactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyFactoryTest {

	@Test
	@DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
	void interfaceProxy() {
		ServiceInterface target = new ServiceImpl();

		// 프록시 팩토리로 proxy 객체 생성, target 정보를 넘기면서 생성한다.
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice()); // 프록시에서 실행할 부가 기능 add

		// 생성된 proxy 객체 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.save();

		Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
	}

	@Test
	@DisplayName("구체 클래스가 있으면 CGLIB 프록시 사용")
	void concreteProxy() {
		ConcreteService target = new ConcreteService();

		// 프록시 팩토리로 proxy 객체 생성, target 정보를 넘기면서 생성한다.
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice()); // 프록시에서 실행할 부가 기능 add

		// 생성된 proxy 객체 반환
		ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.call();

		Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
		Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
	}

	@Test
	@DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반으로 동적 프록시를 생성한다.")
	void proxyTargetClass() {
		ServiceInterface target = new ServiceImpl();

		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true); // 클래스 기반으로 동적 프록시 생성
		proxyFactory.addAdvice(new TimeAdvice());

		// 생성된 proxy 객체 반환
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.save();

		Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
		Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
	}
}
