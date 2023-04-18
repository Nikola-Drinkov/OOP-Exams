package fairyShop.models;

import java.util.List;
import java.util.stream.Collectors;

public class ShopImpl implements Shop {
    @Override
    public void craft(Present present, Helper helper) {
        List<Instrument> suitableInstruments = helper.getInstruments().stream().filter(x->!x.isBroken()).collect(Collectors.toList());
        for(Instrument instrument:suitableInstruments){
            while (helper.canWork() && !present.isDone() && !instrument.isBroken()){
                helper.work();
                instrument.use();
                present.getCrafted();
            }
        }
    }
}
