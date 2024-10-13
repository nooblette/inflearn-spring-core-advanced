package hello.advanced.trace.threadlocal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldServiceTest {
	private FieldService fieldService = new FieldService();

	@Test
	@DisplayName("두 스레드가 순차적으로 작업하는 경우를 테스트한다. (동시성 문제가 발생하지 않음)")
	void field() {
		log.info("main start");

		// fieldService 클래스의 logic() 메서드를 호출하는 작업 정의
		Runnable userA = () -> {
			fieldService.logic("userA");
		};

		// fieldService 클래스의 logic() 메서드를 호출하는 작업 하나 더 정의
		Runnable userB = () -> {
			fieldService.logic("userB");
		};

		// 위 두 작업(userA, userB)을 수행하는 쓰레드 생성
		Thread threadA = new Thread(userA);
		threadA.setName("thread-A"); // 로그에 스레드 이름을 커스텀하게 노출

		Thread threadB = new Thread(userB);
		threadB.setName("thread-B"); // 로그에 스레드 이름을 커스텀하게 노출

		// 스레드 A 작업 시작
		threadA.start(); // userA Runnable 인터페이스를 실행한다.
		// sleep(2000); // 2초간 대기 (동시성 문제가 발생하지 않음)
		sleep(100); // thread A의 작업이 종료되기 전에 thread B의 작업이 시작한다(동시성 문제 발생)

		// 스레드 B 작업 시작
		threadB.start();

		sleep(3000); // 메인 스레드 종료 대기
		log.info("main exit");
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
