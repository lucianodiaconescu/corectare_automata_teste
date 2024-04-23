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
    public String addAnswer(@RequestParam("questionId") Long questionId, @RequestParam("answer") String answer, @RequestParam(value = "correct", required = false) String correct) {
        AnswersEntity newAnswer = new AnswersEntity();
        newAnswer.setIdQuestion(questionId);
        newAnswer.setAnswer(answer);

        String letter =  (char)(answersService.getAllAnswersByQuestionId(questionId).size() + 65) + " ";

        newAnswer.setAnswerLetter(letter);

        if (correct != null)
            newAnswer.setCorrect(true);
        else
            newAnswer.setCorrect(false);

        answersService.addAnswer(newAnswer);
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

    @DeleteMapping("/api/users/delete-answers")
    public String deleteAnswer(@RequestParam("questionId") Long questionId) {
        answersRepository.deleteAllByIdQuestion(questionId);
        return "redirect:/api/users/viewtests";
    }

    @PostMapping("/api/users/edit-answer")
    public String editAnswer(@RequestParam("answerId") Long answerId, @RequestParam("newAnswer") String newAnswerText) {
        AnswersEntity existingAnswer = answersService.getAnswerById(answerId);
        existingAnswer.setAnswer(newAnswerText);
        answersService.updateAnswer(existingAnswer);
        return "redirect:/api/users/viewtests";
    }

}