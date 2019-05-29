package mycontroller.strategies;

import mycontroller.carstatemachine.CarStateSwitch;

import java.util.Queue;

public class FuelConsumerStrategy extends AbstractAutoControllerStrategy implements IAutoControllerStrategy {


    @Override
    public String nextOrientation(CarStateSwitch.CarState state) {
        return null;
    }

    @Override
    public Queue planPath() {
        return null;
    }
}
