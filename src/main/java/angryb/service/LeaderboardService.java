package angryb.service;


import angryb.model.leaderboard.Leaderboard;

import angryb.model.leaderboard.NameAndValue;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class LeaderboardService {

    @Value("${spring.datasource.url}")
    private String dataSourceURL;

    @Value("${spring.datasource.username}")
    private String dataSourceUser;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;


    @Autowired
    public LeaderboardService() {

    }

    public List<Leaderboard> getAllLeaderboards() {

        List<Leaderboard> leaderboards = new ArrayList<>();
        leaderboards.add(createLeaderBoard("select max(GAME.kills), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.kills) desc", "Kills"));
        leaderboards.add(createLeaderBoard("select max(GAME.DEATHS), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.DEATHS) desc", "DEATHS"));
        leaderboards.add(createLeaderBoard("select max(GAME.game_duration)/60, S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.game_duration) desc", "Longest Game"));
        leaderboards.add(createLeaderBoard("select max(GAME.LARGEST_CRITICAL_STRIKE), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.LARGEST_CRITICAL_STRIKE) desc", "Largest Crit"));

        leaderboards.add(createLeaderBoard("select max(GAME.TOTAL_TIME_SPENT_DEAD)/60, S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.TOTAL_TIME_SPENT_DEAD) desc", "Longest time spent dead"));
        leaderboards.add(createLeaderBoard("select max(GAME.TOTAL_MINIONS_KILLED), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.TOTAL_MINIONS_KILLED) desc", "Most minions killed"));
        leaderboards.add(createLeaderBoard("select max(GAME.LARGEST_KILLING_SPREE), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.LARGEST_KILLING_SPREE) desc", "Largest Killingspree"));
        leaderboards.add(createLeaderBoard("select max(GAME.EFFECTIVE_HEAL_AND_SHIELDING), S.NAME from GAME inner join SUMMONER S on S.ID = GAME.SUMMONER_ID group by S.NAME order by max(GAME.EFFECTIVE_HEAL_AND_SHIELDING) desc", "Most Healing/Shielding"));
        return leaderboards;
    }

    private Leaderboard createLeaderBoard(String sql, String name){

        List<NameAndValue> nameAndValues = new ArrayList<>();
        int maxValue = 0;
        try {
            Connection connection = DriverManager.getConnection(dataSourceURL, dataSourceUser, dataSourcePassword);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                nameAndValues.add(new NameAndValue(resultSet.getString(2), resultSet.getInt(1), maxValue));
            }
            resultSet.first();
            maxValue = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new Leaderboard(nameAndValues, name, maxValue);
    }
}
