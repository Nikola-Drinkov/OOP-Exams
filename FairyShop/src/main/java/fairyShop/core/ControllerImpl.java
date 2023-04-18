package fairyShop.core;

import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static fairyShop.common.ConstantMessages.*;
import static fairyShop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private HelperRepository<Helper> helperRepository;
    private PresentRepository<Present> presentRepository;
    private Shop shop;
    private int totalPresents;

    public ControllerImpl() {
        helperRepository = new HelperRepository<>();
        presentRepository = new PresentRepository<>();
        shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        switch (type){
            case"Happy":
                helper = new Happy(helperName);
                helperRepository.add(helper);
                break;
            case"Sleepy":
                helper = new Sleepy(helperName);
                helperRepository.add(helper);
                break;
            default:
                throw new IllegalArgumentException(HELPER_TYPE_DOESNT_EXIST);
        }
        return String.format(ADDED_HELPER,type,helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Helper helper = helperRepository.findByName(helperName);
        if(helper==null) throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        else{
            Instrument instrument = new InstrumentImpl(power);
            helper.addInstrument(instrument);
        }
        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER,power,helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName,energyRequired);
        presentRepository.add(present);
        return String.format(SUCCESSFULLY_ADDED_PRESENT,presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        int brokenInstruments = 0;
        Present present = presentRepository.findByName(presentName);
        List<Helper> suitableHelpers = helperRepository.getModels().stream()
                .filter(x->x.getEnergy()>50)
                .collect(Collectors.toList());
        if(suitableHelpers.isEmpty()) throw new IllegalArgumentException(NO_HELPER_READY);
        for (Helper helper : suitableHelpers){
            shop.craft(present,helper);
            brokenInstruments += helper.getInstruments().stream().filter(Instrument::isBroken).count();
        }
        if(present.isDone()) totalPresents++;
        String status = present.isDone()? "done" : "not done";
        return String.format("Present %s is %s. %d instrument/s have been broken while working on it!",presentName,status,brokenInstruments);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d presents are done!",totalPresents)).append(System.lineSeparator());
        sb.append("Helpers info:").append(System.lineSeparator());

        for(Helper helper : helperRepository.getModels()){
            int suitableInstruments = (int) helper.getInstruments().stream().filter(x->!x.isBroken()).count();
            sb.append(String.format("Name: %s",helper.getName())).append(System.lineSeparator());
            sb.append(String.format("Energy: %s",helper.getEnergy())).append(System.lineSeparator());
            sb.append(String.format("Instruments: %d not broken left",suitableInstruments)).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
