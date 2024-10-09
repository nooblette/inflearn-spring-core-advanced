package hello.advanced.app.v0;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController // @Controller + @ResponseBody, OrderControllerV0 클래스도 Component Scan 대상에 포함됨
@RequiredArgsConstructor
public class OrderControllerV0 {
	private final OrderServiceV0 orderServiceV0;

	@GetMapping("/v0/request")
	public String request(String itemId) {
		orderServiceV0.orderItem(itemId);
		return "ok"; // @ResponseBody에 의해 문자를 API 응답에 반환
	}
}
