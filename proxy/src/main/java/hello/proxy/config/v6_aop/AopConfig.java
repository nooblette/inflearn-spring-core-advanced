package hello.proxy.config.v6_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {
	@Bean
	public LogTraceAspect logTraceAspect(LogTrace logTrace) {
		// LogTraceAspect 클래스가 어드바이저(Advisor)로 사용된다.
		return new LogTraceAspect(logTrace);
	}
}
