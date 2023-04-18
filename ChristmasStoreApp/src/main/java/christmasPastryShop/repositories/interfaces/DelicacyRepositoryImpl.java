package christmasPastryShop.repositories.interfaces;

import christmasPastryShop.entities.delicacies.interfaces.Delicacy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DelicacyRepositoryImpl implements DelicacyRepository<Delicacy> {
    private Collection<Delicacy> delicacies;

    public DelicacyRepositoryImpl() {
        delicacies = new ArrayList<>();
    }

    @Override
    public Delicacy getByName(String name) {
        return delicacies.stream()
                .filter(x->x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Delicacy> getAll() {
        return Collections.unmodifiableCollection(delicacies);
    }

    @Override
    public void add(Delicacy delicacy) {
        delicacies.add(delicacy);
    }
}
