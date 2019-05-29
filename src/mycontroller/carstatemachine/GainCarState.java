package mycontroller.carstatemachine;

import controller.CarController;
import mycontroller.controllerDB.CarWorldMap;

public class GainCarState {
    private static final int MIN_HEALTH = 10;
    private static GainCarState gainCarState;
    private CarController carController;
    private GainCarState(CarController carController){
        this.carController = carController;
    }
    public static GainCarState getInstance(CarController carController){
        if(gainCarState==null){
            gainCarState = new GainCarState(carController);
        }
        return gainCarState;
    }

    public static GainCarState getInstance(){
        if(gainCarState==null){
            System.out.println("Need initial CarController First!");
        }
        return gainCarState;
    }
    public void update(){
        if(carController.numParcelsFound()==carController.numParcels()){
            CarStateSwitch.getInstance().setGetTargetParcels(true);
        }
        if(carController.getHealth()<MIN_HEALTH){
            CarStateSwitch.getInstance().setNeedHealth(true);
        }
        if(CarWorldMap.getInstance().getNumParcels()==carController.numParcels()
                &&!(carController.numParcelsFound()==carController.numParcels())){
            CarStateSwitch.getInstance().setFindTargetParcel(true);
        }
    }

    public CarStateSwitch.CarState getCurrentState(){
        return CarStateSwitch.getInstance().updateState();
    }
}
