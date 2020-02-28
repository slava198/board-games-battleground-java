package by.vyun.service;

import by.vyun.model.BoardGame;
import by.vyun.model.RegistrationException;
import by.vyun.model.User;
import by.vyun.repo.BoardGameRepo;
import by.vyun.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    UserRepo userRepo;
    BoardGameRepo gameRepo;

    public User registration(User user) throws RegistrationException {

        if (user.getLogin().trim().length() * user.getPassword().trim().length() * user.getLocation().trim().length() == 0) {
            throw new RegistrationException("Empty login, password or location field!!!");
        }
        if (userRepo.getFirstByLogin(user.getLogin()) != null) {
            throw new RegistrationException("Login duplicated!!!");
        }
        user = userRepo.save(user);
        return user;
    }

    public User signIn(User user) throws RegistrationException {
        User foundedUser = userRepo.getFirstByLogin(user.getLogin());
        if (foundedUser == null) {
            throw new RegistrationException("Login not founded!!!");
        }
        if (!user.getPassword().equals(foundedUser.getPassword())) {
            throw new RegistrationException("Invalid password!!!");
        }
        return foundedUser;

    }


    public User update(User currentUser, User changedUser) throws RegistrationException {

            currentUser.setLocation(changedUser.getLocation());
            currentUser.setAge(changedUser.getAge());
            currentUser.setPassword(changedUser.getPassword());
            return userRepo.save(currentUser);



     //       return userRepo.save(changedUser);
    }


    public void removeGameById(String login, Integer gameId) {
        User user = userRepo.getFirstByLogin(login);
        BoardGame game = gameRepo.getOne(gameId);

        user.getGameCollection().remove(game);
        userRepo.save(user);

        //user.getGameCollection().remove(game);
        System.out.println(user.getGameCollection().remove(game));
    }
}
