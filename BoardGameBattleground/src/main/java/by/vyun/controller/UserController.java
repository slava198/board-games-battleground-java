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

    @GetMapping("registrationPage")
    public String registrationPage() {
        return "registration";

    }

    @PostMapping("/registration")
    public String registration(User user, Model model) {
        try {
            userService.registration(user);
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";

    }


    @PostMapping("/update")
    public String update(User changedUser, Model model, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            currentUser = userService.update(currentUser, changedUser);
            session.setAttribute("user", currentUser);
            model.addAttribute("user", currentUser);
            return "account";
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";

    }


    @PostMapping("/sign_in")
    public String signIn(User user, HttpSession session, Model model) {
        User signedUser = new User();
        try {
            signedUser = userService.signIn(user);
            session.setAttribute("user", signedUser);
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
//        session.setAttribute("user", signedUser);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("games", signedUser.getGameCollection());
        model.addAttribute("meetings", signedUser.getMeetingSet());

        return "account";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/removeGameById")
    public String removeGameById(String login, Integer id) {

        userService.removeGameById(login, id);
        return "redirect:/";
    }







}
