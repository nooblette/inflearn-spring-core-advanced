package hello.advanced.app.v0;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository // OrderRepositoryV0 클래스를 Component Scan 대상에 포함, 자동으로 스프링 빈으로 등록, @Componenet 어노테이션을 포함하고 있다.
@RequiredArgsConstructor
public class OrderRepositoryV0 {

	public void save(String itemId) {
		// 상품 저장
		if(itemId.equals("ex")) {
			throw new IllegalStateException("예외 발생!");
		}

		sleep(1000); // 상품 저장하는데 1000ms(= 1sec)가 걸린다고 가정(1000ms간 대기)
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
