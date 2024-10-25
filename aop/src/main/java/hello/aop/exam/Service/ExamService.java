package hello.aop.exam.Service;

import org.springframework.stereotype.Service;

import hello.aop.exam.Repository.ExamRepository;
import hello.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;

	@Trace
	public void request(String itemId) {
		examRepository.save(itemId);
	}
}
