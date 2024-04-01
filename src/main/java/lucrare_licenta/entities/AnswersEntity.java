package lucrare_licenta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "answers_entity", schema = "lucrare_lic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_question;
    private String answer;
    private String answerLetter;
    private String correct;
}
