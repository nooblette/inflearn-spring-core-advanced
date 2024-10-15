package hello.proxy.jdkdynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {

	@Test
	void reflection0() {
		Hello target = new Hello();

		// 공통 로직 1 시작
		log.info("start");
		String result1 = target.callA(); // 공통 로직 1과 2는 호출하는 메서드만 다르다.
		log.info("result1={}", result1);
		// 공통 로직 1 종료

		// 공통 로직 2 시작
		log.info("start");
		String result2 = target.callA();
		log.info("result2={}", result2);
		// 공통 로직 2 종료
	}

	@Test
	void reflection1() throws Exception {
		// 클래스 메타 정보를 동적으로 획득
		Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();

		// callA 메서드 정보 획득
		Method methodCallA = classHello.getMethod("callA");
		Object result1 = methodCallA.invoke(target); // target 인스턴으싀 callA 메서드를 동적으로 호출
		log.info("result1={}", result1);

		// callB 메서드 정보 획득
		Method methodCallB = classHello.getMethod("callB");
		Object result2 = methodCallB.invoke(target); // target 인스턴으싀 callA 메서드를 동적으로 호출
		log.info("result2={}", result2);
	}

	@Test
	void reflection2() throws Exception {
		// 클래스 메타 정보를 동적으로 획득
		Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();

		// callA 메서드 정보와 classHello 클래스 정보로 동적으로 호출
		dynamicCall(classHello.getMethod("callA"), target);

		// callB 메서드 정보와 classHello 클래스 정보로 동적으로 호출
		dynamicCall(classHello.getMethod("callB"), target);
	}

	private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
		log.info("start");
		Object result1 = method.invoke(target); // target 인스턴스의 method 메서드를 동적으로 호출하는 로직을 추상화
		log.info("result2={}", result1);
	}

	@Slf4j
	static class Hello {
		public String callA() {
			log.info("callA");
			return "A";
		}

		public String callB() {
			log.info("callB");
			return "B";
		}
	}
}
