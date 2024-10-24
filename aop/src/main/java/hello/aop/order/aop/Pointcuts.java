package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
	// hello.aop.order 패키지와 하위 패키지에 어드바이스(Advice)를 적용
	@Pointcut("execution(* hello.aop.order..*(..))")
	public void allOrder() {} // pointcut signature

	// 클래스 이름 패턴이 *Service인 곳에 어드바이스(Advice) 적용 - 트랜잭션은 보통 비즈니스 로직(서비스 계층)에 두기 때문
	@Pointcut("execution(* *..*Service.*(..))")
	public void allService() {}

	// allOrder와 allService를 함께 적용
	@Pointcut("allOrder() && allService()")
	public void allOrderAndService() {}

}
