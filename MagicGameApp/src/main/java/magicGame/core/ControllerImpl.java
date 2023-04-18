package magicGame.core;

import magicGame.models.magicians.BlackWidow;
import magicGame.models.magicians.Magician;
import magicGame.models.magicians.Wizard;
import magicGame.models.magics.BlackMagic;
import magicGame.models.magics.Magic;
import magicGame.models.magics.RedMagic;
import magicGame.models.region.Region;
import magicGame.models.region.RegionImpl;
import magicGame.repositories.interfaces.MagicRepositoryImpl;
import magicGame.repositories.interfaces.MagicianRepositoryImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static magicGame.common.ExceptionMessages.*;
import static magicGame.common.OutputMessages.SUCCESSFULLY_ADDED_MAGIC;
import static magicGame.common.OutputMessages.SUCCESSFULLY_ADDED_MAGICIAN;

public class ControllerImpl implements Controller{
    private MagicRepositoryImpl magics;
    private MagicianRepositoryImpl magicians;
    private Region region;

    public ControllerImpl() {
        magics = new MagicRepositoryImpl();
        magicians = new MagicianRepositoryImpl();
        region = new RegionImpl();
    }

    @Override
    public String addMagic(String type, String name, int bulletsCount) {
        Magic magic;
        switch (type){
            case"RedMagic":
                magic = new RedMagic(name,bulletsCount);
                magics.addMagic(magic);
                break;
            case"BlackMagic":
                magic = new BlackMagic(name,bulletsCount);
                magics.addMagic(magic);
                break;
            default: throw new IllegalArgumentException(INVALID_MAGIC_TYPE);
        }
        return String.format(SUCCESSFULLY_ADDED_MAGIC,name);
    }

    @Override
    public String addMagician(String type, String username, int health, int protection, String magicName) {
        Magician magician;
        Magic magic = magics.findByName(magicName);
        if(magic==null){
            throw new NullPointerException(MAGIC_CANNOT_BE_FOUND);
        }

        switch (type){
            case"Wizard":
                magician = new Wizard(username,health,protection,magic);
                magicians.addMagician(magician);
                break;
            case"BlackWidow":
                magician = new BlackWidow(username,health,protection,magic);
                magicians.addMagician(magician);
                break;
            default: throw new IllegalArgumentException(INVALID_MAGICIAN_TYPE);
        }
        return String.format(SUCCESSFULLY_ADDED_MAGICIAN,username);
    }

    @Override
    public String startGame() {
        List<Magician> aliveMagicians = magicians.getData().stream().filter(Magician::isAlive).collect(Collectors.toList());
        return region.start(aliveMagicians);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();

        List<Magician> sortedMagicians = magicians.getData().stream()//.sorted((Comparator.comparing(Magician::getHealth).thenComparing(Magician::getUsername))).collect(Collectors.toList());
                .sorted((m1,m2)->{
            if(m1.getHealth()>m2.getHealth()) return 1;
            if(m1.getHealth()<m2.getHealth()) return -1;
            if(m1.getUsername().compareTo(m2.getUsername())>0) return 1;
            if(m1.getUsername().compareTo(m2.getUsername())<0) return -1;
            if(m1.getClass().getSimpleName().compareTo(m2.getClass().getSimpleName())>0) return 1;
            if(m1.getClass().getSimpleName().compareTo(m2.getClass().getSimpleName())<0) return -1;
            return 0;
        }).collect(Collectors.toList());

        for (Magician magician : sortedMagicians){
            sb.append(String.format("%s: %s%n",magician.getClass().getSimpleName(),magician.getUsername()));
            sb.append(String.format("Health: %d%n",magician.getHealth()>0 ? magician.getHealth() : 0));
            sb.append(String.format("Protection: %d%n",magician.getProtection()>0 ? magician.getProtection() : 0));
            sb.append(String.format("Magic: %s%n",magician.getMagic().getName()));
        }
        return sb.toString().trim();
    }
}
