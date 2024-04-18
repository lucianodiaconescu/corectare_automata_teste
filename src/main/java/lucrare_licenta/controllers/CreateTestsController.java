package lucrare_licenta.controllers;

import lucrare_licenta.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateTestsController {

    private final TestsService testsService;
    private final LoginController loginController;

    @Autowired
    public CreateTestsController(TestsService testsService, LoginController loginController) {
        this.testsService = testsService;
        this.loginController = loginController;
    }
    @GetMapping("/api/users/createtests")
    public ModelAndView showCreateTestsPage() {
        ModelAndView modelAndView = new ModelAndView("createtests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);
        return modelAndView;
    }
    @PostMapping("/api/users/createtests")
    public String createTest(String testName) {
        String loggedInEmail = loginController.getLoggedInUserEmail();
        testsService.createTest(loggedInEmail, testName);
        return "redirect:/api/users/viewtests";
    }

}
