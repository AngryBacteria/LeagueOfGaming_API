package angryb.repository;

import angryb.model.Metadata;
import angryb.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {


    Metadata findFirstByOrderByLastUpdatedDesc();
}
