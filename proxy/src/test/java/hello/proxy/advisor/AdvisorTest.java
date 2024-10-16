package hello.proxy.advisor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;

public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface service = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(service);

		// 어드바이저 생성, Pointcut.TRUE = 항상 참
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

		// 프록시 팩토리(proxyFactory)에 어드바이저 add
		proxyFactory.addAdvisor(advisor);

		// 어드바이저가 적용된 동적 프록시 반환
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}
}
