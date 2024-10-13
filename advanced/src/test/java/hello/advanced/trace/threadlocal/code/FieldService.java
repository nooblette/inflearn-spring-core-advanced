package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {
	private String nameStore;

	public String logic(String name) {
		log.info("저장 name={} --> nameStroe={}", name, nameStore);
		nameStore = name;

		// 저장하는데 1초가 걸린다고 가정
		sleep(1000);
		log.info("조회 nameStore={}", nameStore);
		return nameStore;
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
