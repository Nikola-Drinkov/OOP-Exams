package football.core;


import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static football.common.ConstantMessages.*;
import static football.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private SupplementRepository supplement;
    private Map<String, Field> fields;
    public ControllerImpl() {
        this.supplement = new SupplementRepositoryImpl();
        fields = new LinkedHashMap<>();
    }

    @Override
    public String addField(String fieldType, String fieldName) {
        Field field;
        if(!fieldType.equals("ArtificialTurf") && !fieldType.equals("NaturalGrass")) throw new NullPointerException(INVALID_FIELD_TYPE);

        if(fieldType.equals("ArtificialTurf")){
            field = new ArtificialTurf(fieldName);
            fields.put(fieldName, field);
        }
        else{
            field = new NaturalGrass(fieldName);
            fields.put(fieldName, field);
        }
        return String.format(SUCCESSFULLY_ADDED_FIELD_TYPE,fieldType);

    }

    @Override
    public String deliverySupplement(String type) {
        Supplement supp;
        if(!type.equals("Powdered") && !type.equals("Liquid")) throw new IllegalArgumentException(INVALID_SUPPLEMENT_TYPE);

        if(type.equals("Powdered")){
            supp = new Powdered();
            supplement.add(supp);
        }
        else{
            supp = new Liquid();
            supplement.add(supp);
        }
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE,type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {
        Supplement s = supplement.findByType(supplementType);
        if (s==null) throw new IllegalArgumentException(String.format(NO_SUPPLEMENT_FOUND,supplementType));

        Field field = fields.get(fieldName);
        field.addSupplement(s);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD,supplementType,fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {
        Field field = fields.get(fieldName);
        if(!playerType.equals("Men") && !playerType.equals("Women")) throw new IllegalArgumentException(INVALID_PLAYER_TYPE);

        if(playerType.equals("Men")){
            if(field.getClass().getSimpleName().equals("NaturalGrass")){
                Player player = new Men(playerName,nationality,strength);
                field.addPlayer(player);
                return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType,fieldName);
            }
        }
        else {
            if(field.getClass().getSimpleName().equals("ArtificialTurf")){
                Player player = new Women(playerName,nationality,strength);
                field.addPlayer(player);
                return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType,fieldName);
            }
        }
        return FIELD_NOT_SUITABLE;
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = fields.get(fieldName);
        field.drag();
        return String.format(PLAYER_DRAG,field.getPlayers().size());
    }

    @Override
    public String calculateStrength(String fieldName) {
        Field field = fields.get(fieldName);
        int strength = field.getPlayers().stream().mapToInt(Player::getStrength).sum();
        return String.format(STRENGTH_FIELD,fieldName,strength);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        fields.values().forEach(f->sb.append(f.getInfo())
                        .append(System.lineSeparator()));
        return sb.toString();
    }
}
