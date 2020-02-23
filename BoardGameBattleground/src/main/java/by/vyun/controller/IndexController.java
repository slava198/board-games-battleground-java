package by.vyun.controller;

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

    @GetMapping("/")
    public String index() {

        return "index";
    }

}
