package hello.aop.exam.Service;

import org.springframework.stereotype.Service;

import hello.aop.exam.Repository.ExamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;

	public void request(String itemId) {
		examRepository.save(itemId);
	}
}
