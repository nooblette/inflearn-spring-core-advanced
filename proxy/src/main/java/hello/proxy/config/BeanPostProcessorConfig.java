package hello.proxy.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class}) // AppV1Config, AppV2Config는 각 컨트룰러, 서비스, 리포지토리를 스프링 빈으로 수동등록하고 있음
public class BeanPostProcessorConfig {

	// PackageLogTracePostProcessor 객체를 스프링 빈으로 수동 등록
	@Bean
	public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
		return new PackageLogTracePostProcessor("hello.proxy.app", getAdvisor(logTrace));
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		// point cut
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");

		// advice 생성
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);

		// 어드바이저 생성 및 반환 (어드바이저는 하나의 포인트 컷과 하나의 어드바이스를 갖는다.)
		return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
	}
}
