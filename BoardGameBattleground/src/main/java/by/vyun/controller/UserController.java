package by.vyun.controller;

import by.vyun.model.BoardGame;
import by.vyun.model.RegistrationException;
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
        model.addAttribute("games", gameService.getAllGames());
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
            currentUser = userService.update(currentUser, changedUser);
            //session.setAttribute("user", currentUser);
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
            if (signedUser.getLogin().equals("admin")) {
                session.setAttribute("user", signedUser);
                return "redirect:/admin/sign_in";
            }
            session.setAttribute("user", signedUser);
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
//        session.setAttribute("user", signedUser);
        model.addAttribute("user", session.getAttribute("user"));
//        model.addAttribute("games", signedUser.getGameCollection());
//        model.addAttribute("meetings", signedUser.getMeetingSet());

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
        model.addAttribute("user", currentUser);
        model.addAttribute("games", gameService.getAllGames());
        return "game_list";
    }




    @GetMapping("/remove_game")
    public String remove_game(Integer id, HttpSession session, Model model) {
        User user = userService.removeGameById( ((User)(session.getAttribute("user"))).getLogin(), id);
        model.addAttribute("user", user);
        return "account";
    }







}
