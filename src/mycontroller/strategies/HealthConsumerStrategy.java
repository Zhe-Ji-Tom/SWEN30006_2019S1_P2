package mycontroller.strategies;

import controller.CarController;
import mycontroller.carstatemachine.CarStateSwitch;
import mycontroller.controllerDB.CarWorldMap;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.LinkedList;

public class HealthConsumerStrategy extends AbstractAutoControllerStrategy implements IAutoControllerStrategy {
    private CarController carController;

    public void build(CarController carController){
        this.carController = carController;

    }

    private Coordinate current;
    private Coordinate destination;

    @Override
    public String nextOrientation(CarStateSwitch.CarState state) {
        String currentStr = carController.getPosition();
        String destinationStr = CarWorldMap.getInstance().getPoint().toString();
        if(currentStr.equals(destinationStr)){
            return "brake";
        }
        this.current = new Coordinate(currentStr);
        this.destination = new Coordinate(destinationStr);
        return orientation(getOrientation(),relativeOrientation_Desti_Current(this.current,this.destination));
    }

    @Override
    public LinkedList planPath() {
        PathSolver pathSolver = new PathSolver(CarWorldMap.getInstance().getWorld(),carController.getPosition());

        LinkedList path = pathSolver.start();
        CarWorldMap.getInstance().setExitPath(pathSolver.exitRouter);
        path.forEach((value)->System.out.println(value.toString()));
        return path;
    }

    private WorldSpatial.Direction relativeOrientation_Desti_Current(Coordinate current, Coordinate destination){
        if((destination.x-current.x)==1&&(destination.y==current.y)){
            return WorldSpatial.Direction.EAST;
        }else if((destination.x-current.x)==-1&&(destination.y==current.y)){
            return WorldSpatial.Direction.WEST;
        }else if((destination.x==current.x)&&(destination.y-current.y)==1){
            return WorldSpatial.Direction.NORTH;
        }if((destination.x==current.x)&&(destination.y-current.y)==-1){
            return WorldSpatial.Direction.SOUTH;
        } else {
            return getOrientation();
        }

    }

    private WorldSpatial.Direction getOrientation(){
        return carController.getOrientation();
    }


    private void runStrategy(){

    }
}
