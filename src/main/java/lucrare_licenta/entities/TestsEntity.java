package lucrare_licenta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tests_entity", schema = "lucrare_lic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailUser;
    private String testName;
}
