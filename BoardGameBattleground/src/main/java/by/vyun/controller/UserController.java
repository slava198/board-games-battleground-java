package by.vyun.controller;

import by.vyun.model.User;
import by.vyun.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
//@NoArgsConstructor
//@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping("/user/save")
    public String save(User user) {

        //if (user.getNickname())

        userService.saveUser(user);

        return "redirect:/";

    }




}
