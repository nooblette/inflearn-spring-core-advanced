package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {
	private Component component;

	public TimeDecorator(Component component) {
		this.component = component;
	}

	@Override
	public String operation() {
		log.info("TimeDecorator 실행");
		long startTime = System.currentTimeMillis();

		// 프록시에서 프록시를 호출한다. (프록시 체인)
		String result = component.operation();
		long endTime = System.currentTimeMillis();

		long resultTime = endTime - startTime;
		log.info("TimeDecorator 종료 resultTime={} ms", resultTime);
		return result;
	}
}
