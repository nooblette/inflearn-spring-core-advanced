package hello.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

public class BasicTest {

	@Test
	void basicConfig() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class); // 스프링 컨테이너

		// A 인스턴스는 빈으로 등록
		B beanA = applicationContext.getBean("beanA", B.class);
		beanA.helloB();

		// B 인스턴스는 빈으로 등록되지 않는다.
		Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean( A.class));
	}

	@Slf4j
	@Configuration
	static class BeanPostProcessorConfig {
		// A 인스턴스를 beanA 이름으로 스프링 컨테이너에 스프링 빈으로 등록
		@Bean(name = "beanA")
		public A a() {
			return new A();
		}

		@Bean
		public AToBPostProcessor helloPostProcessor() {
			return new AToBPostProcessor();
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
		public void helloB() {
			log.info("helloB");
		}
	}

	@Slf4j
	static class AToBPostProcessor implements BeanPostProcessor {
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			log.info("beanName={}, bean={}", beanName, bean);

			// bean이 A 인스턴스이면 B 인스턴스로 바꿔치기
			if(bean instanceof A) {
				return new B();
			}

			return bean;
		}
	}
}
