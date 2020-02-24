package by.vyun.controller;

import by.vyun.model.RegistrationException;
import by.vyun.model.User;
import by.vyun.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
//@NoArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    public String registration(User user, Model model) {
        try {
            userService.registration(user);
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "index";

    }


    @PostMapping("/update")
    public String update(User updatedUser, Model model, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            session.setAttribute("user", userService.update(currentUser, updatedUser));
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "account";

    }


    @PostMapping("/sign_in")
    public String signIn(User user, HttpSession session, Model model) {
        try {
            session.setAttribute("user", userService.signIn(user));
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        model.addAttribute("user", session.getAttribute("user"));
        return "account";

    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("login");
        return "redirect:/";
    }






}
