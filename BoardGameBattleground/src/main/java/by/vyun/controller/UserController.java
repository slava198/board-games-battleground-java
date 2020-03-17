package by.vyun.controller;

import by.vyun.exception.RegistrationException;
import by.vyun.model.User;
import by.vyun.service.BoardGameService;
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
    BoardGameService gameService;

    @GetMapping("/registrationPage")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("/gameListPage")
    public String gameListPage(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("games", userService.getUnsubscribedGames((User) session.getAttribute("user")));
        return "game_list";
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
            currentUser = userService.update(currentUser.getId(), changedUser);
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
        User signedUser;
        try {
            signedUser = userService.signIn(user);
            session.setAttribute("user", signedUser);
            session.setAttribute("userId", signedUser.getId());
            if (signedUser.getLogin().equals("admin")) {
                return "redirect:/admin/sign_in";
            }
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        model.addAttribute("user", session.getAttribute("user"));
        return "account";

    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }


    @GetMapping("/add_game")
    public String addGame(Integer gameId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        currentUser = userService.addGameToUser(gameId, currentUser);
        session.setAttribute("user", currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("games", userService.getUnsubscribedGames((User) session.getAttribute("user")));
        return "game_list";
    }

    @GetMapping("/remove_game")
    public String removeGame(Integer gameId, HttpSession session, Model model) {
        User user = userService.removeGameById( ((User)(session.getAttribute("user"))).getLogin(), gameId);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return "account";
    }

    @GetMapping("/back")
    public String back(HttpSession session, Model model) {
        session.setAttribute("user", userService.getUserById((Integer) session.getAttribute("userId")));
        model.addAttribute("user", session.getAttribute("user"));
        return "account";
    }





}
