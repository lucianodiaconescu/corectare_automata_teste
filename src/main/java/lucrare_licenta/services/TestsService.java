package lucrare_licenta.services;

import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.repositories.QuestionsRepository;
import lucrare_licenta.repositories.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestsService {

    private final TestsRepository testsRepository;
    private final QuestionsRepository questionsRepository;

    @Autowired
    public TestsService(TestsRepository testsRepository, QuestionsRepository questionsRepository) {
        this.testsRepository = testsRepository;
        this.questionsRepository = questionsRepository;
    }

    public void createTest(String emailUser, String testName) {
        TestsEntity test = new TestsEntity();
        test.setEmailUser(emailUser);
        test.setTestName(testName);
        testsRepository.save(test);
    }

    public List<TestsEntity> getAllTestsForUser(String emailUser) {
        return testsRepository.findAllByEmailUser(emailUser);
    }

    @Transactional
    public void deleteTest(Long testId) {
        TestsEntity test = testsRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Testul nu a fost găsit: " + testId));

        questionsRepository.deleteByidTest(testId);

        testsRepository.delete(test);
    }
}
