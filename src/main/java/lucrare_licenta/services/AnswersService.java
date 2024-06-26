package lucrare_licenta.services;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.repositories.AnswersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersService {
    private final AnswersRepository answersRepository;

    public AnswersService(AnswersRepository answersRepository) {
        this.answersRepository = answersRepository;
    }

    public void addAnswer(AnswersEntity answers) {
        answersRepository.save(answers);
    }

    public List<AnswersEntity> getAllAnswersByQuestionId(Long questionId){
        return answersRepository.findAllByIdQuestion(questionId);
    }

    public List<AnswersEntity> getAnswersForQuestion(Long questionId) {
        return answersRepository.findByIdQuestionOrderById(questionId);
    }

    public void deleteAnswer(Long answerId) {
        answersRepository.deleteById(answerId);
    }

    public void updateAnswer(AnswersEntity answers) {
        answersRepository.save(answers);
    }

    public AnswersEntity getAnswerById(Long answerId) {
        return answersRepository.findById(answerId).orElse(null);
    }

    public void deleteAllAnswersForQuestion(Long questionId) {
        List<AnswersEntity> answers = answersRepository.findAllByIdQuestion(questionId);
        for (AnswersEntity answer : answers) {
            answersRepository.delete(answer);
        }
    }
}
