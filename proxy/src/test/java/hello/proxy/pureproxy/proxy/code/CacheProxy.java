package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

	// 프록시 객체는 실제 객체(Target)를 호출한다.
	private Subject target;
	private String cacheValue;

	public CacheProxy(Subject target) {
		this.target = target;
	}

	@Override
	public String operation() {
		log.info("프록시 호출");

		// 캐시된 데이터가 없다면 실제 객체(Target)를 호출하여 값을 가져온다.
		if(cacheValue == null) {
			cacheValue = target.operation();
		}

		// 두번째 호출부터는 캐시된 데이터가 있으므로 실제 객체(Target)를 호출하지 않는다.
		return cacheValue;
	}
}
