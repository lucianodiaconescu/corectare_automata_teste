package lucrare_licenta.controllers;

import lucrare_licenta.entities.QuestionsEntity;
import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.repositories.QuestionsRepository;
import lucrare_licenta.services.QuestionsService;
import lucrare_licenta.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ViewTestsController {

    private final TestsService testsService;
    private final LoginController loginController;
    private final QuestionsService questionsService;
    private final QuestionsRepository questionsRepository;

    @Autowired
    public ViewTestsController(TestsService testsService, LoginController loginController, QuestionsService questionsService, QuestionsRepository questionsRepository) {
        this.testsService = testsService;
        this.loginController = loginController;
        this.questionsService = questionsService;
        this.questionsRepository = questionsRepository;
    }

    @GetMapping("/api/users/viewtests")
    public ModelAndView showViewTestsPage() {
        ModelAndView modelAndView = new ModelAndView("viewtests");

        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);

        List<TestsEntity> tests = testsService.getAllTestsForUser(loggedInEmail);
        modelAndView.addObject("tests", tests);

        Map<Long, List<QuestionsEntity>> questionsMap = new HashMap<>();
        for (TestsEntity test : tests) {
            List<QuestionsEntity> questions = questionsService.getQuestionsForTest(test.getId());
            questionsMap.put(test.getId(), questions);
        }
        modelAndView.addObject("questionsMap", questionsMap);

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

}
