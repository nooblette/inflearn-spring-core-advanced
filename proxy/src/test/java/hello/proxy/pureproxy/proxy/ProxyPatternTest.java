package hello.proxy.pureproxy.proxy;

import org.junit.jupiter.api.Test;

import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;

public class ProxyPatternTest {
	@Test
	void noProxyTest() {
		RealSubject realSubject = new RealSubject();
		ProxyPatternClient proxyPatternClient = new ProxyPatternClient(realSubject);

		proxyPatternClient.execute();
		proxyPatternClient.execute();
		proxyPatternClient.execute();
	}
}
