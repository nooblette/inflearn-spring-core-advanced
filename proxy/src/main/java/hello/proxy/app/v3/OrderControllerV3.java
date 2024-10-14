package hello.proxy.app.v3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping
@RestController // @ResponseBoy + @Controller, OrderControllerV3 클래스를 컴포넌트 스캔 대상에 포함 (자동 빈 등록)
public class OrderControllerV3 {
	private final OrderServiceV3 orderService;

	public OrderControllerV3(OrderServiceV3 orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/v3/request")
	public String request(String itemId) {
		orderService.orderItem(itemId);
		return "ok";
	}

	@GetMapping("/v3/no-log")
	public String noLog() {
		return "ok";
	}
}
