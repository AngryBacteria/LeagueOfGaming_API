package angryb.old.controllers;

import angryb.old.model.Metadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@RestController
@RequestMapping("/api/metadata")
public class MetaDataController {

    @GetMapping("/latest")
    @ResponseBody
    public Metadata getMetaData(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Metadata> metadata = entityManager
                .createQuery("Select m from Metadata m order by m.id desc", Metadata.class).setMaxResults(1)
                .getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        return metadata.get(0);
    }

    @GetMapping("/")
    @ResponseBody
    public List<Metadata> getMetaAllData(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Metadata> metadata = entityManager
                .createQuery("Select m from Metadata m order by m.id desc", Metadata.class).getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();
        return metadata;
    }

}
