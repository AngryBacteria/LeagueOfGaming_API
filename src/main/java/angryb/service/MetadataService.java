package angryb.service;

import angryb.model.Game;
import angryb.model.Metadata;
import angryb.repository.GameRepository;
import angryb.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataService {


    private final MetadataRepository metadataRepository;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public List<Metadata> getAllMetadata() {
        return this.metadataRepository.findAll();
    }

    public Metadata findLatestMetadata(){
        return this.metadataRepository.findFirstByOrderByLastUpdatedDesc();
    }

    public void updateMetadata(Metadata metadata){
        this.metadataRepository.save(metadata);
    }
}
