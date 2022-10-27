package angryb.old.controllers;
import angryb.old.model.SummerStats;
import angryb.old.model.Summoner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/summoner")
public class SummonerController {

    @GetMapping("/name/{name}")
    @ResponseBody
    public Summoner summonerName(@PathVariable String name) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where upper(a.name) = upper(?1)", Summoner.class);
        query.setParameter(1, name);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return summoners.get(0);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner could not be found");
    }

    @GetMapping("/puuid/{puuid}")
    @ResponseBody
    public Summoner summonerPuuid(@PathVariable String puuid) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where a.puuid = ?1", Summoner.class);
        query.setParameter(1, puuid);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return summoners.get(0);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner could not be found");
    }

    @GetMapping("/stats/name/{name}")
    @ResponseBody
    public SummerStats summonerStatsName(@PathVariable String name) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where upper(a.name) = upper(?1)", Summoner.class);
        query.setParameter(1, name);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return new SummerStats(summoners.get(0));
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner Stats could not be found");
    }

    @GetMapping("/stats/puuid/{puuid}")
    @ResponseBody
    public SummerStats summonerStatsPuuid(@PathVariable String puuid) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where a.puuid = ?1", Summoner.class);
        query.setParameter(1, puuid);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return new SummerStats(summoners.get(0));
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner Stats could not be found");
    }

    @GetMapping("/stats/")
    @ResponseBody
    public List<SummerStats> summonerStatsAll() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Summoner> summoners = entityManager
                .createQuery("Select a from Summoner a", Summoner.class)
                .getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        List<SummerStats> summerStats = new ArrayList<>();
        for (Summoner summoner : summoners){
            summerStats.add(new SummerStats(summoner));
        }

        return summerStats;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Summoner> allSummoners() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Summoner> summoners = entityManager
                .createQuery("Select a from Summoner a", Summoner.class)
                .getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        return summoners;
    }
}
