package by.vyun.controller;

import by.vyun.exception.RegistrationException;
import by.vyun.model.BoardGame;
import by.vyun.model.Location;
import by.vyun.model.Meeting;
import by.vyun.model.User;
import by.vyun.service.BoardGameService;
import by.vyun.service.MeetingService;
import by.vyun.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
//@NoArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserService userService;
    BoardGameService gameService;
    MeetingService meetingService;

    User getCurrentUser() {
        return userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("locations", Location.values());
        return "registration";
    }

    @GetMapping("/update_page")
    public String updatePage(HttpSession session, Model model) {
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("locations", Location.values());
        return "update_account";
    }

    @GetMapping("/gameList_page")
    public String gameListPage(HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("games", userService.getUnsubscribedGames(currentUser));
        return "game_list";
    }

    @GetMapping("/createMeet_page")
    public String createMeet(int gameId, Model model) {
        BoardGame game = gameService.getGameById(gameId);
        model.addAttribute("game", game);
        model.addAttribute("locations", Location.values());
        return "meet_create";
    }


//**********************begin user
    @PostMapping("/registration")
    public String registration(User user, String passwordConfirm, Model model) {
        if (!user.checkPassword(passwordConfirm)) {
            model.addAttribute("error", "Password and it's confirmations are the different!");
            return "registration";
        }
        try {
            userService.registration(user);
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registration";
        }
        return "redirect:/";

    }


    @PostMapping("/update")
    public String update(User changedUser, String newPassword, String newPasswordConfirm, Model model, HttpSession sesssion) {
        try {
            User currentUser = getCurrentUser();
            if (!currentUser.checkPassword(changedUser.getPassword())) {
                model.addAttribute("error", "Invalid current password!");
                model.addAttribute("user", currentUser);
                return "update_account";
            }
            if (!newPassword.equals(newPasswordConfirm)) {
                model.addAttribute("error", "New password and it's confirmations are the different!");
                model.addAttribute("user", currentUser);
                return "update_account";
            }
            changedUser.setPassword(newPassword);
            currentUser = userService.update(currentUser.getId(), changedUser);
            //session.setAttribute("user", currentUser);
            currentUser = userService.getUserById(currentUser.getId());
            model.addAttribute("user", currentUser);
            model.addAttribute("gameCollection", currentUser.getGameCollection());
            model.addAttribute("meetingSet", currentUser.getMeetingSet());
            model.addAttribute("createdMeets", currentUser.getCreatedMeets());
            return "account";
        }
        catch (RegistrationException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";

    }


//    @PostMapping("/sign_in")
//    public String signIn(String login, String password, HttpSession session, Model model) {
//        User signedUser = null;
//
//        try {
//            signedUser = userService.signIn(login, password);
//            session.setAttribute("user", signedUser);
//            //session.setAttribute("userId", signedUser.getId());
//            if (signedUser.getLogin().equals("admin")) {
//                return "redirect:/admin/sign_in";
//            }
//        }
//        catch (RegistrationException ex) {
//            //model.addAttribute("error", ex.getMessage());
//            session.setAttribute("error", ex.getMessage());
//            return "redirect:/";
//        }
//        model.addAttribute("user", session.getAttribute("user"));
//        model.addAttribute("createdMeetings", userService.getCreatedMeets(signedUser));
//        model.addAttribute("gameCollection", signedUser.getGameCollection());
//        model.addAttribute("meetingSet", signedUser.getMeetingSet());
//        model.addAttribute("createdMeets", signedUser.getCreatedMeets());
//
//        return "account";
//
//    }

//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("user");
//        return "redirect:/";
//    }
//*******************************end user


//*****************begin game
    @GetMapping("/add_game")
    public String addGame(Integer gameId, HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        currentUser = userService.addGame(currentUser.getId(), gameId);

        model.addAttribute("user", currentUser);
        model.addAttribute("games", userService.getUnsubscribedGames(getCurrentUser()));
        return "game_list";
    }

    @GetMapping("/remove_game")
    public String removeGame(Integer gameId, HttpSession session, Model model) {
        User currentUser = userService.deleteGame(getCurrentUser().getId(), gameId);
        //session.setAttribute("user", currentUser);
        currentUser = userService.getUserById(currentUser.getId());
        model.addAttribute("user", currentUser);
        model.addAttribute("gameCollection", currentUser.getGameCollection());
        model.addAttribute("meetingSet", currentUser.getMeetingSet());
        model.addAttribute("createdMeets", currentUser.getCreatedMeets());
        return "account";
    }

    @GetMapping("/see_game")
    public String seeGame(Integer gameId, HttpSession session, Model model) {
        //User user = userService.removeGameById( ((User)(session.getAttribute("user"))).getLogin(), gameId);
        BoardGame game = gameService.getGameById(gameId);
        //session.setAttribute("user", user);
        model.addAttribute("game", game);
        model.addAttribute("user", getCurrentUser());
        return "game_account";
    }

//***********************************end game


//********************************begin meet
    @GetMapping("/delete_meet")
    public String deleteMeet(int meetId, HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        currentUser = userService.deleteMeeting(currentUser.getId(), meetId);
        //currentUser.getCreatedMeets().remove()---
        meetingService.removeMeet(meetId);
        //session.setAttribute("user", currentUser);
        currentUser = userService.getUserById(currentUser.getId());
        model.addAttribute("user", currentUser);
        model.addAttribute("gameCollection", currentUser.getGameCollection());
        model.addAttribute("meetingSet", currentUser.getMeetingSet());
        model.addAttribute("createdMeets", currentUser.getCreatedMeets());
        return "account";
    }

    @PostMapping("/create_meet")
    public String createMeet(Location location,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                             int gameId, HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        Meeting meet = new Meeting();
        meet.setLocation(location.name());
        meet.setDateTime(dateTime);
        meet.setGame(gameService.getGameById(gameId));
        meetingService.createMeet(currentUser.getId(), meet);
        //currentUser = userService.takePartInMeeting(currentUser.getId(), meet.getId());
        //session.setAttribute("user", currentUser);
        currentUser = userService.getUserById(currentUser.getId());
        model.addAttribute("user", currentUser);
        model.addAttribute("game", gameService.getGameById(gameId));

        return "game_account";
    }

    @GetMapping("/add_meet")
    public String addMeeting(int meetId, HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        currentUser = userService.takePartInMeeting(currentUser.getId(), meetId);
        //session.setAttribute("user", currentUser); //-???
        model.addAttribute("game", meetingService.getMeetingById(meetId).getGame());
        model.addAttribute("user", currentUser);
        return "game_account";
    }

    @GetMapping("/leave_meet")
    public String leaveMeeting(int meetId, HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        currentUser = userService.leaveMeeting(currentUser.getId(), meetId);
        //session.setAttribute("user", currentUser);
        //model.addAttribute("game", meetingService.getMeetingById(meetId).getGame());
        currentUser = userService.getUserById(currentUser.getId());
        model.addAttribute("user", currentUser);
        model.addAttribute("gameCollection", currentUser.getGameCollection());
        model.addAttribute("meetingSet", currentUser.getMeetingSet());
        model.addAttribute("createdMeets", currentUser.getCreatedMeets());
        return "account";
    }

    //**********************************end meet

    @GetMapping("/back")
    public String back(HttpSession session, Model model) {
        User currentUser = getCurrentUser();
        currentUser = userService.getUserById(currentUser.getId());
        model.addAttribute("user", currentUser);
        model.addAttribute("gameCollection", currentUser.getGameCollection());
        model.addAttribute("meetingSet", currentUser.getMeetingSet());
        model.addAttribute("createdMeets", currentUser.getCreatedMeets());
        return "account";
    }







}
