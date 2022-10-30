package angryb.service;

import angryb.model.Game;
import angryb.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {


    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getGamesByURL(String gameurl){
        return gameRepository.findGamesByGameURL(gameurl);
    }

    public long numberOfGames(){
        return this.gameRepository.count();
    }
}
