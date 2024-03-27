package lucrare_licenta.controllers;

import lucrare_licenta.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/api/users/register")
    public ModelAndView registerUser(
            @RequestParam("firstname") String firstName,
            @RequestParam("lastname") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        try {
            registerService.registerUser(firstName, lastName, email, password);
            modelAndView.setViewName("redirect:/login");
        } catch (IllegalArgumentException e) {
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }
}
