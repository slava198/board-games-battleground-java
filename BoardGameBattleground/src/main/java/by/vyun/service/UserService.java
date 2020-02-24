package by.vyun.service;

import by.vyun.model.RegistrationException;
import by.vyun.model.User;
import by.vyun.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    UserRepo userRepo;

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
       //if (currentUser.getLogin().equals(changedUser.getLogin())) {
            changedUser.setId(currentUser.getId());
            changedUser.setLogin(currentUser.getLogin());
            userRepo.delete(currentUser);
            return userRepo.save(changedUser);
    }



}
