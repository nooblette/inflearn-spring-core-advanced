package hello.proxy.app.v3;

import org.springframework.stereotype.Repository;

@Repository // OrderRepositoryV3 클래스를 컴포넌트 스캔에 포함 (자동 빈 등록)
public class OrderRepositoryV3 {
	public void save(String itemId) {
		// 저장 로직
		if (itemId.equals("ex")) {
			throw new IllegalArgumentException("예외 발생");
		}
		sleep(1000);
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
