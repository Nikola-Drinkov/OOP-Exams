package robotService.entities.services;

public class SecondaryService extends BaseService{
    private static final int SECONDARY_CAPACITY = 15;
    public SecondaryService(String name) {
        super(name, SECONDARY_CAPACITY);
    }
}
