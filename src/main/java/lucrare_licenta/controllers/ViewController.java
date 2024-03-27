package lucrare_licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("/createtests")
    public String showCreateTestsPage() {
        return "redirect:/login";
    }
    @GetMapping("/scantests")
    public String showScanTestsPage() {
        return "redirect:/login";
    }
    @GetMapping("/viewtests")
    public String showViewTestsPage() {
        return "redirect:/login";
    }
    @GetMapping("/logged")
    public String showLoggedPage() {
        return "redirect:/login";
    }
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
}
