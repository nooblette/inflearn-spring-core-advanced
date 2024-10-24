package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionTest {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		// 출력 결과 : public java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
		// pointcut 지시자의 경로와 메서드명, 파라미터를 이 helloMethod 출력값과 매칭할 수 있다.
		log.info("helloMethod={}", helloMethod);
	}

	@Test
	void exactMatch() {
		// public java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
		pointcut.setExpression("execution(public String hello.aop.order.aop.member.MemberServiceImpl.hello(String))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch() {
		// 메서드 이름을 정확히 매치
		pointcut.setExpression("execution(* hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

	}

	@Test
	void namePatternMatchStar1() {
		// 메서드 이름이 hel로 시작하는 메서드에 매치
		pointcut.setExpression("execution(* hel*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void namePatternMatchStar2() {
		// 메서드 이름 중간에 el이 있는 메서드에 매치
		pointcut.setExpression("execution(* *el*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchFalse() {
		// 메서드 매치에 실패하는 경우
		pointcut.setExpression("execution(* none(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void packageExecution() {
		// 패지키지명과 메서드명이 정확히 일치
		pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packagePathMatch() {
		// 패지키지명만 매치 - 해당 패키지의 하위 클래스에 매치
		pointcut.setExpression("execution(* hello.aop.order.aop.member.*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packagePathMatchFalse() {
		// 패지키지명만 매치 실패 - hello.aop 패키지에 바로 속하는 클래스에만 매치
		pointcut.setExpression("execution(* hello.aop.*.*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void packageMatchSubPackage() {
		// 서브 패키지까지 매치 - hello.aop.order와 하위 패키지에 속하는 클래스에 매치
		pointcut.setExpression("execution(* hello.aop.order..*..*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
