package lucrare_licenta.repositories;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<AnswersEntity, Long> {
    List<AnswersEntity> findAllByIdQuestion(Long idQuestion);
}
