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

    @GetMapping("/gameListPage")
    public List<BoardGame> gameListPage(int userId) {
        User currentUser = userService.getUserById(userId);
        return userService.getUnsubscribedGames(currentUser);
    }

    //              REQUESTS WITH PATH /USER
    @PostMapping("/user")
    public String registration(@RequestBody User user) {
        try {
            userService.registration(user);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return "ok";
    }

    @GetMapping("/user")
    public User signIn(String login, String password) {
        User signedUser = new User();
        try {
            signedUser = userService.signIn(login, password);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
        }
        return signedUser;
    }

    @PutMapping("/user")
    public User update(@RequestBody int userId, @RequestBody User changedUser) {
        User currentUser = new User();
        try {
            currentUser = userService.update(userId, changedUser);
        }
        catch (RegistrationException ex) {
            System.out.println(ex.getMessage());
        }
        return currentUser;
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



//    @PostMapping("/user/game")
//    public User addGame(int userId, int gameId) {
//        return userService.addGameToUser(gameId, userService.getUserById(userId));
//
//    }
//
//    @DeleteMapping("/user/game")
//    public User removeGame(int userId, int gameId) {
//        return userService.removeGameById(userService.getUserById(userId).getLogin(), gameId);
//    }


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
