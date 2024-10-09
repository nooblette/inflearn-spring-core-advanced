package hello.advanced.app.v0;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service // OrderServiceV0 클래스를 Component Scan 대상에 포함, 자동으로 스프링 빈으로 등록, @Componenet 어노테이션을 포함하고 있다.
@RequiredArgsConstructor // final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class OrderServiceV0 {
	private final OrderRepositoryV0 orderRepository;

	public void orderItem(String itemId) {
		// 상품 저장
		orderRepository.save(itemId);
	}
}
