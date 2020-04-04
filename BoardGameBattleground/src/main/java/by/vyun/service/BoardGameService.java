package by.vyun.service;


import by.vyun.model.BoardGame;
import by.vyun.repo.BoardGameRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BoardGameService {
    BoardGameRepo gameRepo;

    public List<BoardGame> getAllGames() {
        return gameRepo.findAll();
    }

    public BoardGame getGameById(int id) {
        return gameRepo.getFirstById(id);
    }


    public BoardGame add(BoardGame game) {
        return gameRepo.save(game);
    }

    public void changeGameStatus(int id) {
        BoardGame game = gameRepo.getOne(id);
        game.setIsActive(!game.getIsActive());
        gameRepo.saveAndFlush(game);

    }



}
