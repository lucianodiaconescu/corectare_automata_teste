package lucrare_licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ViewController {
    @Autowired
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
    @GetMapping("/logged")
    public String showLoggedPage() {
        return "redirect:/login";
    }
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
    @GetMapping("/api/users/home")
    public RedirectView showLogoutPage() {
        return new RedirectView("/login");
    }

}
