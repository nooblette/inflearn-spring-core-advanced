package hello.aop.proxyvs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시 기반 프록시 생성
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 기반 프록시 생성
@Import(ProxyDIAspect.class)
public class ProxyDITest {
	@Autowired
	MemberService memberService;

	@Autowired
	MemberServiceImpl memberServiceImpl;

	@Test
	void go() {
		log.info("memberService class={}", memberService.getClass());
		log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
		memberServiceImpl.hello("hello");
	}
}
