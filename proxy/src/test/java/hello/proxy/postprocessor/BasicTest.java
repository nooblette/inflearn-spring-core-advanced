package hello.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

public class BasicTest {

	@Test
	void basicConfig() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class); // 스프링 컨테이너

		// A 인스턴스는 빈으로 등록
		A beanA = applicationContext.getBean("beanA", A.class);
		beanA.helloA();

		// B 인스턴스는 빈으로 등록되지 않는다.
		Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean("beanB", A.class));
	}

	@Slf4j
	@Configuration
	static class BasicConfig {
		// A 인스턴스를 beanA 이름으로 스프링 컨테이너에 스프링 빈으로 등록
		@Bean(name = "beanA")
		public A a() {
			return new A();
		}
	}


	@Slf4j
	static class A {
		public void helloA() {
			log.info("helloA");
		}
	}

	@Slf4j
	static class B {
		public void helloA() {
			log.info("helloB");
		}
	}
}
