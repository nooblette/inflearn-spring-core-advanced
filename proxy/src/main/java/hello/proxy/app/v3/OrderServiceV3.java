package hello.proxy.app.v3;

import org.springframework.stereotype.Service;

@Service // OrderServiceV3 클래스를 컴포넌트 스캔에 포함 (자동 빈 등록)
public class OrderServiceV3 {
	private final OrderRepositoryV3 orderRepository;

	public OrderServiceV3(OrderRepositoryV3 orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
