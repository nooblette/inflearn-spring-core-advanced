package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.order.aop.member.MemberServiceImpl;

public class ArgsTest {
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	private AspectJExpressionPointcut pointcut(String expression) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		return pointcut;
	}

	@Test
	void args() {
		// hello(String)과 매칭
		assertThat(pointcut("args(String)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(Object)") // Object는 모든 자바 객체의 최상위 타입(부모 타입도 대체 가능)
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args()") // 아무것도 없는 메서드에 적용
			.matches(helloMethod, MemberServiceImpl.class)).isFalse(); // helloMethod()는 파라미터가 존재하므로 매칭되지 않음
		assertThat(pointcut("args(..)") // 파라미터의 개수에 상관없이 매칭(즉, 파라미터가 없거나 여러 개가 있어도 매칭)
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(*)") // 타입 상관없이 반드시 하나의 파라미터만 존재하는 메서드에 적용
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(String, ..)") // 첫번째 파라미터는 반드시 String, 이후 파라미터는 상관없음
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	/**
	 * execution(* *(java.io.Serializable)) : 메서드의 시그니처로 판단 (정적)
	 * args(java.io.Serializable) : 런타임에 전달된 인수로 판단 (동적)
	 */
	@Test
	void argsVsExecution() {
		// args - 매개변수의 타입이 일치하는지와 상위 타입도 허용
		assertThat(pointcut("args(String)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(java.io.Serializable)") // Serializable : 객체를 일련의 바이트로 변환(직렬화)하는 인터페이스
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(Object)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();

		// execution - 매개변수의 타입이 정확히 매칭해야함 (상위타입을 허용하지 않음)
		assertThat(pointcut("execution(* *(String))")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("execution(* *(java.io.Serializable))") // 매칭 실패
			.matches(helloMethod, MemberServiceImpl.class)).isFalse();
		assertThat(pointcut("execution(* *(Object))") // 매칭 실패
			.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}
}
