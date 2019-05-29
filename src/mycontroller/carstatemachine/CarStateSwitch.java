package mycontroller.carstatemachine;

import world.Car;

public class CarStateSwitch {
    public void setGetTargetParcels(boolean getTargetParcels) {
        isGetTargetParcels = getTargetParcels;
    }

    public void setNeedHealth(boolean needHealth) {
        isNeedHealth = needHealth;
    }

    public void setFindTargetParcel(boolean findTargetParcel) {
        isFindTargetParcel = findTargetParcel;
    }

    private boolean isGetTargetParcels;
    private boolean isNeedHealth;
    private boolean isFindTargetParcel;
    private CarStateSwitch(){}
    private static CarStateSwitch carStateSwitch;
    protected static CarStateSwitch getInstance(){
        if(carStateSwitch==null){
            carStateSwitch = new CarStateSwitch();
        }
        return carStateSwitch;
    }
    public enum CarState{
        EXPLORE,
        GETPARCEL,
        GOTOEXIT,
        ADDHEALTH
    }
    protected CarState updateState(){
        if(isGetTargetParcels) return CarState.GOTOEXIT;
        if(isNeedHealth) return CarState.ADDHEALTH;
        if(isFindTargetParcel&&!isGetTargetParcels) return CarState.GETPARCEL;
        return CarState.EXPLORE;
    }
}
