package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {
	private ThreadLocal<String> nameStore = new ThreadLocal<>(); // 필드를 직접 사용하는 대신 ThreadLocal<T>로 감싸준다.

	public String logic(String name) {
		log.info("저장 name={} --> nameStore={}", name, nameStore.get());
		nameStore.set(name);

		// 저장하는데 1초가 걸린다고 가정
		sleep(1000);
		log.info("조회 nameStore={}", nameStore.get());
		return nameStore.get();
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
