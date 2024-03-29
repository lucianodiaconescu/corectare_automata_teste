package lucrare_licenta.controllers;

import lucrare_licenta.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/users/login")
    public ModelAndView loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();

        boolean isAuthenticated = loginService.authenticateUser(email, password);
        if (isAuthenticated) {
            modelAndView.addObject("email", email);
            modelAndView.setViewName("logged");
        } else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}