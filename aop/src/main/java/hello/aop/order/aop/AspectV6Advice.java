package hello.aop.order.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV6Advice {
	//@Around("hello.aop.order.aop.Pointcuts.allOrderAndService()")
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			// @Before
			log.info("[트랜잭션 시작] {}", joinPoint.getSignature());

			// 실제 비즈니스 로직 실행
			Object result = joinPoint.proceed();

			// @AfterReturning
			log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

			return result;
		} catch (Exception e) {
			// @AfterThrowing
			log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
			throw e;
		} finally {
			// @After
			log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
		}
	}

	@Before("hello.aop.order.aop.Pointcuts.allOrderAndService()")
	public void doBefore(JoinPoint joinPoint) {
		// joinPoint 실행 전 로직을 작성
		log.info("[before] {}", joinPoint.getSignature());

		// @Before는 스프링이 타깃 클래스를 실행해준다. (개발자가 joinPoint.proceed()을 호출할 필요 없음)
	}

	@AfterReturning(
		value = "hello.aop.order.aop.Pointcuts.allOrderAndService()",
		returning = "result" // return 값의 이름(변수명)을 작성해주어야함
	)
	public void doReturn(JoinPoint joinPoint, Object result) {
		// return 값(e.g. result)을 임의로 바꿀수는 없다.
		log.info("[afterReturning] {}, result = {}", joinPoint.getSignature(), result);
	}

	@AfterThrowing(
		value = "hello.aop.order.aop.Pointcuts.allOrderAndService()",
		throwing = "ex" // 예외 클래스의 이름(변수명)을 작성해주어야함
	)
	public void doThrowing(JoinPoint joinPoint, Exception ex) {
		log.info("[afterThrowing] {}, ex = {}", joinPoint.getSignature(), ex);

		// 자동으로 ex 예외를 밖으로 던진다. (따로 throw 해줄 필요 없음)
	}

	@After(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()")
	public void doAfter(JoinPoint joinPoint) {
		// finally 키워드와 유사한 역할
		log.info("[after] {}", joinPoint.getSignature());
	}
}
