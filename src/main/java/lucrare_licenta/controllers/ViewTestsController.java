package lucrare_licenta.controllers;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.repositories.AnswersRepository;
import lucrare_licenta.repositories.QuestionsRepository;
import lucrare_licenta.services.AnswersService;
import lucrare_licenta.services.QuestionsService;
import lucrare_licenta.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewTestsController {

    private final TestsService testsService;
    private final LoginController loginController;
    private final QuestionsService questionsService;
    private final QuestionsRepository questionsRepository;
    private final AnswersService answersService;
    private final AnswersRepository answersRepository;

    @Autowired
    public ViewTestsController(TestsService testsService, LoginController loginController, QuestionsService questionsService, QuestionsRepository questionsRepository, AnswersService answersService, AnswersRepository answersRepository) {
        this.testsService = testsService;
        this.loginController = loginController;
        this.questionsService = questionsService;
        this.questionsRepository = questionsRepository;
        this.answersService = answersService;
        this.answersRepository = answersRepository;
    }

    @GetMapping("/api/users/viewtests")
    public ModelAndView showViewTestsPage() {
        ModelAndView modelAndView = new ModelAndView("viewtests");

        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);

        List<TestsEntity> tests = testsService.getAllTestsForUser(loggedInEmail);
        modelAndView.addObject("tests", tests);

        Map<Long, List<QuestionsEntity>> questionsMap = new HashMap<>();
        Map<Long, List<AnswersEntity>> answersMap = new HashMap<>();

        for (TestsEntity test : tests) {
            List<QuestionsEntity> questions = questionsService.getQuestionsForTest(test.getId());
            questionsMap.put(test.getId(), questions);

            for (QuestionsEntity question : questions) {
                Long questionId = question.getId();

                List<AnswersEntity> answers = answersService.getAnswersForQuestion(questionId);

                for (AnswersEntity answer : answers) {
                    answer.setAnswerLetter(answer.getAnswerLetter());
                }
                answersMap.put(questionId, answers);
            }
        }

        modelAndView.addObject("questionsMap", questionsMap);
        modelAndView.addObject("answersMap", answersMap);

        return modelAndView;
    }




    @PostMapping("/api/users/delete-test")
    public String deleteTest(@RequestParam("testId") Long testId) {
        testsService.deleteTest(testId);
        return "redirect:/api/users/viewtests";
    }

    @PostMapping("/api/users/add-question")
    public String addQuestion(@RequestParam("testId") Long testId, @RequestParam("question") String question) {
        QuestionsEntity newQuestion = new QuestionsEntity();
        newQuestion.setIdTest(testId);
        newQuestion.setQuestion(question);

        questionsService.addQuestion(newQuestion);

        return "redirect:/api/users/viewtests";
    }

    @GetMapping("/api/users/questions/{testId}")
    @ResponseBody
    public List<QuestionsEntity> getQuestionsForTest(@PathVariable("testId") Long testId) {
        return questionsService.getQuestionsForTest(testId);
    }

    @PostMapping("/api/users/add-answer")
    public String addAnswer(@RequestParam("questionId") Long questionId,
                            @RequestParam("answer") String answer,
                            @RequestParam(value = "correct", required = false) String correct) {

        // Obține toate răspunsurile pentru întrebarea dată
        List<AnswersEntity> existingAnswers = answersService.getAllAnswersByQuestionId(questionId);

        // Creează o listă de litere disponibile (A, B, C, etc.)
        List<Character> availableLetters = new ArrayList<>();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            availableLetters.add(letter);
        }

        // Elimină literele deja folosite
        for (AnswersEntity existingAnswer : existingAnswers) {
            char usedLetter = existingAnswer.getAnswerLetter().charAt(0);
            availableLetters.remove(Character.valueOf(usedLetter));
        }

        // Verifică dacă mai există litere disponibile
        if (availableLetters.isEmpty()) {
            // Nu mai sunt litere disponibile, poți trata această situație aici
            // În acest exemplu, poți să nu mai adaugi răspunsul sau să faci altceva în consecință
            // În cazul de față, voi întoarce doar o redirecționare la pagina de vizualizare a testelor
            return "redirect:/api/users/viewtests";
        }

        // Obține prima literă disponibilă și o folosește pentru noul răspuns
        char newLetter = availableLetters.get(0);
        String letter = String.valueOf(newLetter);

        // Creează un nou obiect de răspuns și setează valorile
        AnswersEntity newAnswer = new AnswersEntity();
        newAnswer.setIdQuestion(questionId);
        newAnswer.setAnswer(answer);
        newAnswer.setAnswerLetter(letter);

        // Setează corectitudinea răspunsului
        newAnswer.setCorrect(correct != null);

        // Adaugă noul răspuns în baza de date
        answersService.addAnswer(newAnswer);

        // Redirecționează la pagina de vizualizare a testelor
        return "redirect:/api/users/viewtests";
    }

    @PostMapping("/api/users/edit-question")
    public String editQuestion(@RequestParam("questionId") Long questionId, @RequestParam("newQuestion") String newQuestionText) {
        QuestionsEntity existingQuestion = questionsService.getQuestionById(questionId);
        existingQuestion.setQuestion(newQuestionText);
        questionsService.updateQuestion(existingQuestion);
        return "redirect:/api/users/viewtests";
    }

    @PostMapping("/api/users/delete-question")
    public String deleteQuestion(@RequestParam("questionId") Long questionId) {
        questionsService.deleteQuestion(questionId);
        return "redirect:/api/users/viewtests";
    }
    @PostMapping("/api/users/delete-answer")
    public String deleteAnswer(@RequestParam("answerId") Long answerId) {
        answersService.deleteAnswer(answerId);
        return "redirect:/api/users/viewtests";
    }
    @PostMapping("/api/users/edit-answer")
    public String editAnswer(@RequestParam("answerId") Long answerId, @RequestParam("newAnswer") String newAnswerText) {
        AnswersEntity existingAnswer = answersService.getAnswerById(answerId);
        existingAnswer.setAnswer(newAnswerText);
        answersService.updateAnswer(existingAnswer);
        return "redirect:/api/users/viewtests";
    }
    @PostMapping("/api/users/delete-all-answers")
    public String deleteAllAnswers(@RequestParam("questionId") Long questionId) {
        answersService.deleteAllAnswersForQuestion(questionId);
        return "redirect:/api/users/viewtests";
    }

}