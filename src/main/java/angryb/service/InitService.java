package angryb.service;

import angryb.model.Game;
import angryb.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InitService {

    private final SummonerService summonerService;
    private final R4JService r4JService;

    @Autowired
    public InitService(SummonerService summonerService, R4JService r4JService) {
        this.summonerService = summonerService;
        this.r4JService = r4JService;
    }

    public void initializeSummoners(){

        this.summonerService.persistSummoners(r4JService.createAllSummoners());
    }

    public void addGamesToSummoners(){

        List<Summoner> summoners = this.summonerService.getAllSummoners();

        for (Summoner summoner : summoners){
            r4JService.addGamesToSummonerToDB(summoner, summonerService);
        }
    }
}
