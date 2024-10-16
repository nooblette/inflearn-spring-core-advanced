package hello.proxy.common.service;

import lombok.extern.slf4j.Slf4j;

// 인터페이스가 없이 구체 클래스만 존재
@Slf4j
public class ConcreteService {
	public void call() {
		log.info("ConcreteService 호출");
	}
}
