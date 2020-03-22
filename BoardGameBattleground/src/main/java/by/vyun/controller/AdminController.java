package by.vyun.controller;

import by.vyun.model.BoardGame;
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
@RequestMapping("/admin")
public class AdminController {
    UserService userService;
    BoardGameService gameService;

    @GetMapping("registrationPage")
    public String registrationPage() {
        return "registration";

    }

    @PostMapping("/add_game")
    public String addGame(BoardGame game, Model model, HttpSession session) {
        try {
            gameService.add(game);
        }
        finally {
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("games", gameService.getAllGames());
            return "admin_page";
        }
//        catch (RegistrationException ex) {
//            model.addAttribute("error", ex.getMessage());
//        }


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


    @GetMapping("/sign_in")
    public String signIn(HttpSession session, Model model) {

        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("games", gameService.getAllGames());
        return "admin_page";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/changeGameStatusById")
    public String changeGameStatusById(Integer id, HttpSession session, Model model) {
        gameService.changeGameStatusById(id);
        session.setAttribute("games", gameService.getAllGames());
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("games", gameService.getAllGames());
        return "admin_page";
    }









}
