package goldDigger.core;

import goldDigger.models.discoverer.Anthropologist;
import goldDigger.models.discoverer.Archaeologist;
import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.discoverer.Geologist;
import goldDigger.models.operation.Operation;
import goldDigger.models.operation.OperationImpl;
import goldDigger.models.spot.Spot;
import goldDigger.models.spot.SpotImpl;
import goldDigger.repositories.DiscovererRepository;
import goldDigger.repositories.SpotRepository;

import java.util.List;
import java.util.stream.Collectors;

import static goldDigger.common.ConstantMessages.*;
import static goldDigger.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private DiscovererRepository discovererRepository;
    private SpotRepository spotRepository;
    private int spotCount;
    public ControllerImpl() {
        discovererRepository = new DiscovererRepository();
        spotRepository = new SpotRepository();
    }
    @Override
    public String addDiscoverer(String kind, String discovererName) {
        Discoverer discoverer;
        switch (kind){
            case"Anthropologist":
                discoverer = new Anthropologist(discovererName);
                discovererRepository.add(discoverer);
                break;
            case"Archaeologist":
                discoverer = new Archaeologist(discovererName);
                discovererRepository.add(discoverer);
                break;
            case"Geologist":
                discoverer = new Geologist(discovererName);
                discovererRepository.add(discoverer);
                break;
            default: throw new IllegalArgumentException(DISCOVERER_INVALID_KIND);
        }
        return String.format(DISCOVERER_ADDED,discoverer.getClass().getSimpleName(),discoverer.getName());
    }

    @Override
    public String addSpot(String spotName, String... exhibits) {
        Spot spot = new SpotImpl(spotName);
        for(String exhibit : exhibits){
            spot.getExhibits().add(exhibit);
        }
        spotRepository.add(spot);
        return String.format(SPOT_ADDED,spotName);
    }

    @Override
    public String excludeDiscoverer(String discovererName) {
        Discoverer discoverer = discovererRepository.byName(discovererName);
        if(discoverer!=null){
            discovererRepository.remove(discoverer);
            return String.format(DISCOVERER_EXCLUDE,discovererName);
        }
        else throw new IllegalArgumentException(String.format(DISCOVERER_DOES_NOT_EXIST,discovererName));
    }

    @Override
    public String inspectSpot(String spotName) {
        List<Discoverer> excludedDiscoverers;
        List<Discoverer> suitableDiscoverers = discovererRepository.getCollection()
                                                                    .stream()
                                                                    .filter(x->x.getEnergy()>45)
                                                                    .collect(Collectors.toList());
        if(suitableDiscoverers.size()==0){
            throw new IllegalArgumentException(SPOT_DISCOVERERS_DOES_NOT_EXISTS);
        }
        else{
            Spot spot = spotRepository.byName(spotName);
            Operation operation = new OperationImpl();
            operation.startOperation(spot,suitableDiscoverers);
            spotCount++;

            excludedDiscoverers = suitableDiscoverers.stream()
                                                    .filter(x->x.getEnergy()==0)
                                                    .collect(Collectors.toList());
        }
        return String.format(INSPECT_SPOT,spotName,excludedDiscoverers.size());
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FINAL_SPOT_INSPECT,spotCount)).append(System.lineSeparator());
        sb.append(FINAL_DISCOVERER_INFO).append(System.lineSeparator());
        for (Discoverer discoverer : discovererRepository.getCollection()){
            sb.append(String.format(FINAL_DISCOVERER_NAME,discoverer.getName())).append(System.lineSeparator());
            sb.append(String.format(FINAL_DISCOVERER_ENERGY,discoverer.getEnergy())).append(System.lineSeparator());

            StringBuilder exhibits = new StringBuilder();
            if(discoverer.getMuseum().getExhibits().isEmpty()){
                exhibits.append("None").append(System.lineSeparator());
            }
            else {
                exhibits.append(String.join(FINAL_DISCOVERER_MUSEUM_EXHIBITS_DELIMITER,discoverer.getMuseum().getExhibits())).append(System.lineSeparator());
            }
            sb.append(String.format("Museum exhibits: %s",exhibits));
        }
        return sb.toString().trim();
    }
}
