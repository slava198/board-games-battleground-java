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


    public BoardGame add(BoardGame game) {
        return gameRepo.save(game);
    }

    public void removeGameById(Integer id) {
        gameRepo.deleteById(id);
    }


}
