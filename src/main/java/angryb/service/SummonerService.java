package angryb.service;

import angryb.model.Summoner;
import angryb.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SummonerService {

    private final SummonerRepository summonerRepository;

    @Autowired
    public SummonerService(SummonerRepository summonerRepository) {
        this.summonerRepository = summonerRepository;
    }

    public List<Summoner> getAllSummoners(){
        return summonerRepository.findAll();
    }

    public void persistSummoner(Summoner summoner){
        summonerRepository.save(summoner);
    }

    public void persistSummoners(List<Summoner> summoners){
        summonerRepository.saveAll(summoners);
    }
}
