package angryb.controllers;

import angryb.model.leaderboard.Leaderboard;
import angryb.model.leaderboard.NameAndValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderBoardController {


    @GetMapping("/")
    @ResponseBody
    public List<Leaderboard> leaderBoard() {

        List<Leaderboard> leaderboards = new ArrayList<>();
        leaderboards.add(createLeaderBoard("select max(GAME.kills), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.kills) desc", "Kills"));
        leaderboards.add(createLeaderBoard("select max(GAME.DEATHS), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.DEATHS) desc", "DEATHS"));
        leaderboards.add(createLeaderBoard("select max(GAME.GAMEDURATION)/60, S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.GAMEDURATION) desc", "Longest Game"));
        leaderboards.add(createLeaderBoard("select max(GAME.LARGESTCRITICALSTRIKE), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.LARGESTCRITICALSTRIKE) desc", "Largest Crit"));

        leaderboards.add(createLeaderBoard("select max(GAME.TOTALTIMESPENTDEAD)/60, S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.TOTALTIMESPENTDEAD) desc", "Longest time spent dead"));
        leaderboards.add(createLeaderBoard("select max(GAME.TOTALMINIONSKILLED), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.TOTALMINIONSKILLED) desc", "Most minions killed"));
        leaderboards.add(createLeaderBoard("select max(GAME.LARGESTKILLINGSPREE), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.LARGESTKILLINGSPREE) desc", "Largest Killingspree"));
        leaderboards.add(createLeaderBoard("select max(GAME.EFFECTIVEHEALANDSHIELDING), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.EFFECTIVEHEALANDSHIELDING) desc", "Most Healing/Shielding"));
        return leaderboards;
    }

    private Leaderboard createLeaderBoard(String sql, String name){

        List<NameAndValue> nameAndValues = new ArrayList<>();
        int maxValue = 0;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            maxValue = resultSet.getInt(1);
            while (resultSet.next()){
                nameAndValues.add(new NameAndValue(resultSet.getString(2), resultSet.getInt(1), maxValue));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new Leaderboard(nameAndValues, name, maxValue);
    }


}
