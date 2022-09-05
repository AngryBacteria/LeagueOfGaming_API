import com.example.demo.model.Summoner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();

        Summoner summoner = entityManager.find(Summoner.class, 1L);
        System.out.println(summoner);

    }

}
