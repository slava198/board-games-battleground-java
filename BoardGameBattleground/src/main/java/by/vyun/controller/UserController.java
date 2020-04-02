package by.vyun.controller;

import by.vyun.exception.RegistrationException;
import by.vyun.model.BoardGame;
import by.vyun.model.Meeting;
import by.vyun.model.User;
import by.vyun.service.BoardGameService;
import by.vyun.service.MeetingService;
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
    MeetingService meetingService;

    @GetMapping("/registration_page")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("/update_page")
    public String updatePage(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "update_account";
    }

    @GetMapping("/gameList_page")
    public String gameListPage(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("games", userService.getUnsubscribedGames((User) session.getAttribute("user")));
        return "game_list";
    }

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
    public String update(User changedUser, String newPassword, String newPasswordConfirm, Model model, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            if (!currentUser.checkPassword(changedUser.getPassword())) {
                model.addAttribute("error", "Invalid current password!");
                model.addAttribute("user", session.getAttribute("user"));
                return "update_account";
            }
            if (!newPassword.equals(newPasswordConfirm)) {
                model.addAttribute("error", "New password and it's confirmations are the different!");
                model.addAttribute("user", session.getAttribute("user"));
                return "update_account";
            }
            changedUser.setPassword(newPassword);
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
        User signedUser = null;

        try {
            signedUser = userService.signIn(user);
            session.setAttribute("user", signedUser);
            //session.setAttribute("userId", signedUser.getId());
            if (signedUser.getLogin().equals("admin")) {
                return "redirect:/admin/sign_in";
            }
        }
        catch (RegistrationException ex) {
            //model.addAttribute("error", ex.getMessage());
            session.setAttribute("error", ex.getMessage());
            return "redirect:/";
        }
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("createdMeetings", userService.getCreatedMeets(signedUser));
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

    @GetMapping("/see_game")
    public String seeGame(Integer gameId, HttpSession session, Model model) {
        //User user = userService.removeGameById( ((User)(session.getAttribute("user"))).getLogin(), gameId);
        BoardGame game = gameService.getGameById(gameId);
        //session.setAttribute("user", user);
        model.addAttribute("game", game);
        model.addAttribute("user", session.getAttribute("user"));
        return "game_account";
    }



    @GetMapping("/create_meet")
    public String createMeet(int gameId, Model model) {
        BoardGame game = gameService.getGameById(gameId);
//        User currentUser = (User) session.getAttribute("user");
//        currentUser = userService.takePartInMeeting(currentUser.getId(), meetingId);
//        session.setAttribute("user", currentUser);
//        model.addAttribute("user", currentUser);
        model.addAttribute("game", game);
        return "meet_create";
    }

    @GetMapping("/delete_meet")
    public String deleteMeet(int meetId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        currentUser = userService.leaveMeeting(currentUser.getId(), meetId);
        meetingService.removeMeet(meetId);
        session.setAttribute("user", currentUser);
        model.addAttribute("user", currentUser);
        return "account";
    }

    @PostMapping("/new_meet")
    public String newMeeting(Meeting meet, int gameId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        meet.setGame(gameService.getGameById(gameId));
        meetingService.createMeet(currentUser, meet);
        currentUser = userService.takePartInMeeting(currentUser.getId(), meet.getId());
        session.setAttribute("user", currentUser);
        model.addAttribute("user", currentUser);
        return "account";
    }

    @GetMapping("/add_meet")
    public String addMeeting(int meetId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        currentUser = userService.takePartInMeeting(currentUser.getId(), meetId);
        model.addAttribute("game", meetingService.getMeetingById(meetId).getGame());
        model.addAttribute("user", currentUser);
        return "game_account";
    }

    @GetMapping("/leave_meet")
    public String leaveMeeting(int meetId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        currentUser = userService.leaveMeeting(currentUser.getId(), meetId);
        session.setAttribute("user", currentUser);
        model.addAttribute("game", meetingService.getMeetingById(meetId).getGame());
        model.addAttribute("user", currentUser);
        return "account";
    }


    @GetMapping("/back")
    public String back(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "account";
    }





}
