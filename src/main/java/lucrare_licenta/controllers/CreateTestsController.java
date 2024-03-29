package lucrare_licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateTestsController {

    private final LoginController loginController;

    @Autowired
    public CreateTestsController(LoginController loginController) {
        this.loginController = loginController;
    }

    @GetMapping("/api/users/createtests")
    public ModelAndView showCreateTestsPage() {
        ModelAndView modelAndView = new ModelAndView("createtests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);
        return modelAndView;
    }
}
