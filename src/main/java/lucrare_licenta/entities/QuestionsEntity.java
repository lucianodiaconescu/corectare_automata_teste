package lucrare_licenta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "questions_entity", schema = "lucrare_lic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_test;
    private String question;
}
