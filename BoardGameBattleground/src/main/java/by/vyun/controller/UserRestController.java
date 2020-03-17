package by.vyun.controller;

import by.vyun.exception.RegistrationException;
import by.vyun.model.BoardGame;
import by.vyun.model.User;
import by.vyun.service.BoardGameService;
import by.vyun.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/mobile")
public class UserRestController {
    UserService userService;
    BoardGameService gameService;

    @PostMapping("/gameListPage")
    public List<BoardGame> gameListPage(int userId) {
        User currentUser = userService.getUserById(userId);
        return userService.getUnsubscribedGames(currentUser);
    }

    @PostMapping("/registration")
    public String registration(User user) {
        try {
            userService.registration(user);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return "ok";
    }

//    @GetMapping("/registration/{login}&{password}")
//    public String registration(@PathVariable String login, @PathVariable String password) {
//        try {
//            User user = new User();
//            user.setLogin(login);
//            user.setPassword(password);
//            user.setLocation("brest");
//            userService.registration(user);
//        }
//        catch (RegistrationException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return "ok";
//    }


    @PostMapping("/update")
    public User update(int userId, User changedUser) {
        User currentUser = new User();
        try {
            currentUser = userService.update(userId, changedUser);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
        }
        return currentUser;

    }


    @GetMapping("/sign_in/{login}&{password}")
    public User signIn(@PathVariable String login, @PathVariable String password) {
        User signedUser = new User();
        try {
            signedUser = userService.signIn(login, password);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
        }
        return signedUser;
    }

//    @GetMapping("/sign_in/{login}&{password}")
//    public ResponseEntity<User> signIn(@PathVariable String login, @PathVariable String password) {
//        User signedUser = new User();
//        List<Map<String, String>> response = new ArrayList<>();
//        try {
//            signedUser = userService.signIn(login, password);
//        }
//        catch (RegistrationException ex) {
//            System.out.println("err");
//            throw new UserNotFoundException(login);
//        }
//        return new ResponseEntity<>(signedUser, HttpStatus.OK);
//    }



    @PostMapping("/add_game")
    public User addGame(int userId, int gameId) {
        return userService.addGameToUser(gameId, userService.getUserById(userId));

    }

    @PostMapping("/remove_game")
    public User removeGame(int userId, int gameId) {
        return userService.removeGameById(userService.getUserById(userId).getLogin(), gameId);
    }


//    @GetMapping("/back")
//    public String back(HttpSession session, Model model) {
//        session.setAttribute("user", userService.getUserById((Integer) session.getAttribute("userId")));
//        model.addAttribute("user", session.getAttribute("user"));
//        return "account";
//    }

    //    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("user");
//        return "redirect:/";
//    }


}
