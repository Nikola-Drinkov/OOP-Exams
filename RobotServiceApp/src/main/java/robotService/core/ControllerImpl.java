package robotService.core;

import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;
import robotService.repositories.SupplementRepository;

import java.util.ArrayList;
import java.util.Collection;

import static robotService.common.ConstantMessages.*;
import static robotService.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private SupplementRepository supplements;
    private Collection<Service> services;

    public ControllerImpl() {
        supplements = new SupplementRepository();
        services = new ArrayList<>();
    }

    @Override
    public String addService(String type, String name) {
        Service service;
        switch (type){
            case"MainService":
                service = new MainService(name);
                break;
            case"SecondaryService":
                service = new SecondaryService(name);
                break;
            default:
                throw new NullPointerException(INVALID_SERVICE_TYPE);
        }
        services.add(service);
        return String.format(SUCCESSFULLY_ADDED_SERVICE_TYPE,type);
    }

    @Override
    public String addSupplement(String type) {
        Supplement supplement;
        switch (type){
            case"PlasticArmor":
                supplement = new PlasticArmor();
                break;
            case"MetalArmor":
                supplement = new MetalArmor();
                break;
            default:
                throw new IllegalArgumentException(INVALID_SUPPLEMENT_TYPE);
        }
        supplements.addSupplement(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE,type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Service service = services.stream().filter(x->x.getName().equals(serviceName)).findFirst().orElse(null);
        Supplement supplement = supplements.findFirst(supplementType);
        if(supplement==null) throw new IllegalArgumentException(String.format(NO_SUPPLEMENT_FOUND,supplementType));

        service.addSupplement(supplement);
        supplements.removeSupplement(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE,supplementType,serviceName);
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        Robot robot;
        Service service = services.stream().filter(x->x.getName().equals(serviceName)).findFirst().orElse(null);
        switch (robotType){
            case"MaleRobot":
                robot = new MaleRobot(robotName,robotKind,price);
                if(!service.getClass().getSimpleName().equals("MainService"))
                    return UNSUITABLE_SERVICE;
                break;
            case"FemaleRobot":
                robot = new FemaleRobot(robotName,robotKind,price);
                if(!service.getClass().getSimpleName().equals("SecondaryService"))
                    return UNSUITABLE_SERVICE;
                break;
            default:
                throw new IllegalArgumentException(INVALID_ROBOT_TYPE);
        }
        service.addRobot(robot);
        return String.format(SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE,robotType,serviceName);
    }

    @Override
    public String feedingRobot(String serviceName) {
        Service service = services.stream().filter(x->x.getName().equals(serviceName)).findFirst().orElse(null);
        service.feeding();
        return String.format(FEEDING_ROBOT,service.getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {
        Service service = services.stream().filter(x->x.getName().equals(serviceName)).findFirst().orElse(null);
        double sum = 0;
        sum += service.getRobots().stream().mapToDouble(Robot::getPrice).sum();
        sum += service.getSupplements().stream().mapToDouble(Supplement::getPrice).sum();

        return String.format(VALUE_SERVICE,serviceName,sum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Service service : services){
            sb.append(service.getStatistics());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
