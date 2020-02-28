package by.vyun.controller;

import by.vyun.service.BoardGameService;
import by.vyun.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@NoArgsConstructor
@AllArgsConstructor
public class IndexController {

    UserService userService;
    BoardGameService gameService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("games", gameService.getAllGames());

        return "index";
    }

}
