package by.vyun.service;

import by.vyun.model.User;
import by.vyun.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    UserRepo userRepo;

    public User saveUser(User user){



        user = userRepo.save(user);
        System.out.println(user.getId());
        return user;
    }



}
