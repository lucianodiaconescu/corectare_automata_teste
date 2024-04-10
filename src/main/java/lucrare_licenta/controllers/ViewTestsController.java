package lucrare_licenta.controllers;

import lucrare_licenta.entities.TestsEntity;
import lucrare_licenta.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ViewTestsController {

    private final TestsService testsService;
    private final LoginController loginController;

    @Autowired
    public ViewTestsController(TestsService testsService, LoginController loginController) {
        this.testsService = testsService;
        this.loginController = loginController;
    }

    @GetMapping("/api/users/viewtests")
    public ModelAndView showViewTestsPage() {
        ModelAndView modelAndView = new ModelAndView("viewtests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);
        List<TestsEntity> tests = testsService.getAllTestsForUser(loggedInEmail);
        modelAndView.addObject("tests", tests);

        //questions get logic

        return modelAndView;
    }
    @PostMapping("/api/users/delete-test")
    public String deleteTest(@RequestParam("testId") Long testId) {
        testsService.deleteTest(testId);
        return "redirect:/api/users/viewtests";
    }

}