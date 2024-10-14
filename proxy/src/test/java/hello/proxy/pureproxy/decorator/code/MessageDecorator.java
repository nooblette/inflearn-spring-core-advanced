package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {
	private Component component; // 타깃 클래스

	public MessageDecorator(Component component) {
		this.component = component;
	}

	@Override
	public String operation() {
		log.info("MessageDecorator 실행");

		// 프록시 객체(MessageDecorator)에서 실제 객체(Component)를 호출하고 응답 결과를 꾸며준다.
		String result = component.operation();

		// data -> *****data*****
		String decoResult = "*****" + result + "*****";
		log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoResult);
		return decoResult;
	}
}
