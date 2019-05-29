package mycontroller.strategies;

import mycontroller.carstatemachine.CarStateSwitch;

import java.util.Queue;

public interface IAutoControllerStrategy {
    String nextOrientation(CarStateSwitch.CarState state);
    Queue planPath();
}
