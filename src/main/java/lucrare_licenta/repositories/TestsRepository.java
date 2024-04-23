package lucrare_licenta.repositories;

import lucrare_licenta.entities.TestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsRepository extends JpaRepository<TestsEntity, Long> {
    List<TestsEntity> findAllByEmailUser(String emailUser);

}
