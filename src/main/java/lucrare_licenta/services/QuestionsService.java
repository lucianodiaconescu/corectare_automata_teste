package lucrare_licenta.services;

import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public void addQuestion(QuestionsEntity question) {
        questionsRepository.save(question);
    }
    public List<QuestionsEntity> getQuestionsForTest(Long testId) {
        return questionsRepository.findByIdTest(testId);
    }
}