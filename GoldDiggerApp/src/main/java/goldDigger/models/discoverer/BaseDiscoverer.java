package goldDigger.models.discoverer;

import goldDigger.models.museum.BaseMuseum;
import goldDigger.models.museum.Museum;

import static goldDigger.common.ExceptionMessages.DISCOVERER_ENERGY_LESS_THAN_ZERO;
import static goldDigger.common.ExceptionMessages.DISCOVERER_NAME_NULL_OR_EMPTY;

public abstract class BaseDiscoverer implements Discoverer {
    private String name;
    private double energy;
    private Museum museum;

    public BaseDiscoverer(String name, double energy) {
        setName(name);
        setEnergy(energy);
        museum = new BaseMuseum();
    }

    private void setName(String name) {
        if(name==null || name.trim().isEmpty()) throw new NullPointerException(DISCOVERER_NAME_NULL_OR_EMPTY);
        this.name = name;
    }

    private void setEnergy(double energy) {
        if(energy<0) throw new IllegalArgumentException(DISCOVERER_ENERGY_LESS_THAN_ZERO);
        this.energy = energy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public boolean canDig() {
        return energy>0;
    }

    @Override
    public Museum getMuseum() {
        return museum;
    }

    @Override
    public void dig() {
        if(energy-15<0) energy = 0;
        else energy-=15;
    }
}
