package angryb.service;

import angryb.model.Metadata;
import angryb.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InitService {

    private final SummonerService summonerService;
    private final GameService gameService;
    private final R4JService r4JService;
    private final MetadataService metadataService;

    @Autowired
    public InitService(SummonerService summonerService, GameService gameService, R4JService r4JService, MetadataService metadataService) {
        this.summonerService = summonerService;
        this.gameService = gameService;
        this.r4JService = r4JService;
        this.metadataService = metadataService;
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

    public void updateMetadata(){

        long numberOfSummoners = this.summonerService.numberOfSummoners();
        long numberOfGames = this.gameService.numberOfGames();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy - HH:mm");

        this.metadataService.updateMetadata(new Metadata(LocalDateTime.now().format(formatter),
                numberOfSummoners, numberOfGames));
    }
}
