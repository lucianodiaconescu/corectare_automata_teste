package lucrare_licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewTestsController {
    private final LoginController loginController;

    @Autowired
    public ViewTestsController(LoginController loginController) {
        this.loginController = loginController;
    }

    @GetMapping("/api/users/viewtests")
    public ModelAndView showScanTestsPage() {
        ModelAndView modelAndView = new ModelAndView("viewtests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);
        return modelAndView;
    }
}
