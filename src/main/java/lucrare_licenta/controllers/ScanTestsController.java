package lucrare_licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScanTestsController {
    private final LoginController loginController;

    @Autowired
    public ScanTestsController(LoginController loginController) {
        this.loginController = loginController;
    }

    @GetMapping("/api/users/scantests")
    public ModelAndView showScanTestsPage() {
        ModelAndView modelAndView = new ModelAndView("scantests");
        String loggedInEmail = loginController.getLoggedInUserEmail();
        modelAndView.addObject("email", loggedInEmail);
        return modelAndView;
    }
}
