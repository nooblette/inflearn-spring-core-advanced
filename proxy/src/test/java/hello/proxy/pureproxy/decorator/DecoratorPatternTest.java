package hello.proxy.pureproxy.decorator;

import org.junit.jupiter.api.Test;

import hello.proxy.pureproxy.decorator.code.Component;
import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecoratorPatternTest {
	@Test
	void noDecorator() {
		Component realComponent = new RealComponent();
		DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
		client.execute();
	}

	@Test
	void decorator1() {
		Component realComponent = new RealComponent();

		// 실제 객체(realComponent, 타깃 클래스)를 주입하여 프록시 객체 생성
		Component messageDecorator = new MessageDecorator(realComponent);

		// 클라이언트는 프록시 객체를 호출한다.
		DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
		client.execute();
	}

	@Test
	void decorator2() {
		Component realComponent = new RealComponent();

		// 실제 객체(realComponent, 타깃 클래스)를 주입하여 프록시 객체 생성
		// messageDecorator 프록시 객체가 타깃 클래스에 의존한다.
		Component messageDecorator = new MessageDecorator(realComponent);

		// timeDecorator 프록시 객체는 messageDecorator 프록시 객체에 의존한다.
		TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);

		// 클라이언트는 timeDecorator 프록시 객체에 의존하고 호출한다.
		DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
		client.execute();
	}
}
