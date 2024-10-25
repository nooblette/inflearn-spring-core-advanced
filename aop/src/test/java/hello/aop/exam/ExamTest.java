package hello.aop.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.exam.Service.ExamService;
import hello.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import(TraceAspect.class) // Asepct를 스프링 빈으로 등록하기 위함
public class ExamTest {
	@Autowired
	ExamService examService;

	@Test
	void test() {
		for(int i = 0; i < 5; i++) {
			examService.request("data" + i);
		}
	}
}
