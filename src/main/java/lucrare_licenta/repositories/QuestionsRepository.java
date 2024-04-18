package lucrare_licenta.repositories;

import lucrare_licenta.entities.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<QuestionsEntity, Long> {
    void deleteByidTest(Long idTest);
    List<QuestionsEntity> findByIdTest(Long testId);
}
