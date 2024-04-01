package lucrare_licenta.services;

import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.repositories.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestsService {

    private final TestsRepository testsRepository;

    @Autowired
    public TestsService(TestsRepository testsRepository) {
        this.testsRepository = testsRepository;
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

    public void deleteTest(Long testId) {
        TestsEntity test = testsRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Testul nu a fost găsit: " + testId));
        testsRepository.delete(test);
    }
}
