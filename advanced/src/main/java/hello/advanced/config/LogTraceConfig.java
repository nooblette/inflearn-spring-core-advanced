package hello.advanced.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;

// @Configuration :
// 	- 해당 클래스가 스프링 설정 클래스임을 나타내며, 스프링 빈을 정의하고 등록하는 역할을 한다.
// 	- @Component 어노테이션이 포함되어 있어 LogTraceConfig 클래스도 컴포넌트 스캔 대상에 포함된다.
@Configuration
public class LogTraceConfig {
	// @Bean :
	//  - FieldLogTrace 객체를 스프링 컨테이너에 수동으로 싱글톤으로 빈으로 등록한다.
	//  - 스프링 컨테이너는 @Bean 메서드를 호출하여 반환된 객체를 스프링 빈으로 등록하고 관리한다.
	@Bean
	public LogTrace logTrace() {
		// 싱글톤으로 스프링 빈으로 등록
		// 동시성 문제가 있는 FieldLogTrace 대신에 문제를 해결한 ThreadLocalLogTrace 클래스를 스프링 빈으로 등록한다.
		return new ThreadLocalLogTrace();
	}
}
