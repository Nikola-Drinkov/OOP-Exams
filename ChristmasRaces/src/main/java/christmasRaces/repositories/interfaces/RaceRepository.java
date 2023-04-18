package christmasRaces.repositories.interfaces;

import christmasRaces.entities.races.Race;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RaceRepository implements Repository<Race> {
    private Collection<Race> races;

    public RaceRepository() {
        races = new ArrayList<>();
    }

    @Override
    public Race getByName(String name) {
        return races.stream().filter(x->x.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<Race> getAll() {
        return Collections.unmodifiableCollection(races);
    }

    @Override
    public void add(Race race) {
        races.add(race);
    }

    @Override
    public boolean remove(Race race) {
        return races.remove(race);
    }
}
