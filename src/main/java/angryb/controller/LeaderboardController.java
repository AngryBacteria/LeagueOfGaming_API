package angryb.controller;

import angryb.model.Game;
import angryb.model.leaderboard.Leaderboard;
import angryb.model.timeline.TimeLine;
import angryb.service.GameService;
import angryb.service.LeaderboardService;
import angryb.service.R4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderBoardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Leaderboard> leaderboards() {
        return leaderBoardService.getAllLeaderboards();
    }
}
