package by.vyun.service;

import by.vyun.model.BoardGame;
import by.vyun.exception.RegistrationException;
import by.vyun.model.Meeting;
import by.vyun.model.User;
import by.vyun.repo.BoardGameRepo;
import by.vyun.repo.MeetingRepo;
import by.vyun.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    UserRepo userRepo;
    BoardGameRepo gameRepo;
    MeetingRepo meetingRepo;

    public User getUserById(Integer id) {
        return userRepo.getFirstById(id);
    }

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

    public User signIn(String login, String password) throws RegistrationException {
        User foundedUser = userRepo.getFirstByLogin(login);
        if (foundedUser == null) {
            throw new RegistrationException("Login not founded!!!");
        }
        if (!password.equals(foundedUser.getPassword())) {
            throw new RegistrationException("Invalid password!!!");
        }
        return foundedUser;
    }



    public User update(int id, User changedUser) throws RegistrationException {
            User currentUser = userRepo.getFirstById(id);
            currentUser.setLocation(changedUser.getLocation());
            currentUser.setAge(changedUser.getAge());
            currentUser.setPassword(changedUser.getPassword());
            return userRepo.saveAndFlush(currentUser);

    }


    public User removeGameById(String login, int gameId) {
        User user = userRepo.getFirstByLogin(login);
        BoardGame game = gameRepo.getOne(gameId);
        user.deleteGameFromCollection(game);
        return userRepo.saveAndFlush(user);
    }

    public User addGameToUser(int gameId, User currentUser) {
        BoardGame game = gameRepo.getFirstById(gameId);
        if (!currentUser.getGameCollection().contains(game)) {
            currentUser.addGameToCollection(game);
        }
        return userRepo.saveAndFlush(currentUser);
    }

    public List<BoardGame> getUnsubscribedGames(User currentUser) {
//        List<BoardGame> unsubscribedGames = new ArrayList<>();
        List<BoardGame> allGames = gameRepo.findAll();
        for (BoardGame game : currentUser.getGameCollection()) {
            if (allGames.contains(game)) {
                allGames.remove(game);
            }
        }
        return allGames;
    }

    public void createMeet() {};

    public User takePartInMeeting(int userId, int meetingId) {
        User currentUser = userRepo.getFirstById(userId);
        currentUser.addMeeting(meetingRepo.getFirstById(meetingId));
        return userRepo.saveAndFlush(currentUser);
    }

    public User leaveMeeting(int userId, int meetingId) {
        User currentUser = userRepo.getFirstById(userId);
        currentUser.deleteMeeting(meetingRepo.getFirstById(meetingId));
        return userRepo.saveAndFlush(currentUser);
    }

    //public Meeting createMeeting()





    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
