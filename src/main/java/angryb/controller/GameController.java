package angryb.controller;

import angryb.model.Game;
import angryb.model.timeline.TimeLine;
import angryb.service.GameService;
import angryb.service.R4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/game")
public class GameController {

    private final GameService gameService;
    private final R4JService r4JService;

    @Autowired
    public GameController(GameService gameService, R4JService r4JService) {
        this.gameService = gameService;
        this.r4JService = r4JService;
    }

    @GetMapping("/db/")
    @ResponseBody
    public List<Game> allGames() {

        return gameService.getAllGames();
    }

    @GetMapping("/db/gameurl/{gameurl}")
    @ResponseBody
    public List<Game> gamesByURL(@PathVariable String gameurl) {

        List<Game> game = gameService.getGamesByURL(gameurl);

        if (game.size() > 0)
            return game;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Games could not be found");
    }

    //TODO problem : gameurl can be in database multiple times
    @GetMapping("/riot/gameid/{gameid}")
    @ResponseBody
    public String gameFromRiot(@PathVariable String gameid) {

        return r4JService.getJsonFromUrl(String.format
                ("https://europe.api.riotgames.com/lol/match/v5/matches/%s?api_key=%s",
                        gameid, r4JService.getCreds().getLoLAPIKey()));
    }

    @GetMapping("/riot/timeline/{gameid}")
    @ResponseBody
    public TimeLine timeLineFromRiot(@PathVariable String gameid) {

        return r4JService.getTimeLineFromGame(gameid);
    }
}
