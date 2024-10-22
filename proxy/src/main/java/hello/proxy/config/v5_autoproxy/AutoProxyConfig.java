package hello.proxy.config.v5_autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

	@Bean
	public Advisor advisor1(LogTrace logTrace) {
		// point cut
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");

		// advice 생성
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);

		// 어드바이저 생성 및 반환 (어드바이저는 하나의 포인트 컷과 하나의 어드바이스를 갖는다.)
		return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
	}
}
