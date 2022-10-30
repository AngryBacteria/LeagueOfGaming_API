package angryb.controller;

import angryb.model.Game;
import angryb.model.Metadata;
import angryb.model.timeline.TimeLine;
import angryb.service.GameService;
import angryb.service.MetadataService;
import angryb.service.R4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/metadata")
public class MetadataController {

    private final MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }


    @GetMapping("/latest")
    @ResponseBody
    public Metadata getMetaData(){
        return this.metadataService.findLatestMetadata();
    }

    @GetMapping("/")
    @ResponseBody
    public List<Metadata> getMetaAllData(){
        return metadataService.getAllMetadata();
    }
}
