package hello.proxy.proxyfactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import hello.proxy.common.advice.TimeAdvice;
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
}
