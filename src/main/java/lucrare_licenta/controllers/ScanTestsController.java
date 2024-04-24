package lucrare_licenta.controllers;

import lucrare_licenta.entities.AnswersEntity;
import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.services.AnswersService;
import lucrare_licenta.services.QuestionsService;
import lucrare_licenta.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScanTestsController {
    private final LoginController loginController;
    private final TestsService testsService;
    private final QuestionsService questionsService;
    private final AnswersService answersService;

    @Autowired
    public ScanTestsController(LoginController loginController, TestsService testsService, QuestionsService questionsService, AnswersService answersService) {
        this.loginController = loginController;
        this.testsService = testsService;
        this.questionsService = questionsService;
        this.answersService = answersService;
    }

    @GetMapping("/api/users/scantests")
    public ModelAndView showScanTestsPage() {
        ModelAndView modelAndView = new ModelAndView("scantests");
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

}
