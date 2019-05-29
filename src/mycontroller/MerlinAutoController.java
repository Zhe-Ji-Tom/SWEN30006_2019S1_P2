package mycontroller;

import controller.CarController;
import mycontroller.carstatemachine.CarStateSwitch;
import mycontroller.carstatemachine.GainCarState;
import mycontroller.controllerDB.CarWorldMap;
import mycontroller.strategies.HealthConsumerStrategy;
import mycontroller.strategies.IAutoControllerStrategy;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

import java.util.*;

public class MerlinAutoController extends MyAutoController {
    // How many minimum units the wall is away from the player.
    private int wallSensitivity = 1;
    private Coordinate destinationPoint;
    private CarWorldMap carWorldMap;
    private IAutoControllerStrategy iAutoControllerStrategy;
    private boolean isAlgorithmRun = false;
    private String currentCommand;
    private String lastCommand;
    // Car Speed to move at
    private final int CAR_MAX_SPEED = 1;
    /**
     * Instantiates the car
     *
     * @param car
     */
    public MerlinAutoController(Car car) {
        super(car);
        carWorldMap = CarWorldMap.getInstance();
        iAutoControllerStrategy = new HealthConsumerStrategy();
        ((HealthConsumerStrategy) iAutoControllerStrategy).build(this);
        carWorldMap.build(getMap());
        carWorldMap.newPath(iAutoControllerStrategy.planPath());
        GainCarState.getInstance(this);
    }

    @Override
    public void update() {
        // Gets what the car can see
        HashMap<Coordinate, MapTile> currentView = getView();
        carWorldMap.refreshMap(currentView);
        GainCarState.getInstance().update();
        if(!isAlgorithmRun){
            currentCommand = iAutoControllerStrategy.nextOrientation(GainCarState.getInstance().getCurrentState());
            switcher(currentCommand);
            lastCommand = currentCommand;
            isAlgorithmRun = true;
        }else {
            if(getSpeed() < CAR_MAX_SPEED){       // Need speed to turn and progress toward the exit
                switcher(lastCommand);   // Tough luck if there's a wall in the way
            }else {
                currentCommand = iAutoControllerStrategy.nextOrientation(GainCarState.getInstance().getCurrentState());
                switcher(currentCommand);
                lastCommand = currentCommand;
            }

        }
    }

    private void switcher(String command){
        switch (command){
            case "right":
                turnRight();
                break;
            case "left":
                turnLeft();
                break;
            case "reverse":
                applyReverseAcceleration();
                break;
            case "forward":
                applyForwardAcceleration();
                break;
            case "brake":
                applyBrake();
                break;
        }
    }

    /**
     * Check if the wall is on your right hand side given your orientation
     * @param orientation
     * @param currentView
     * @return
     */
    private boolean checkRightFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {

        switch(orientation){
            case EAST:
                return checkSouth(currentView);
            case NORTH:
                return checkEast(currentView);
            case SOUTH:
                return checkWest(currentView);
            case WEST:
                return checkNorth(currentView);
            default:
                return false;
        }
    }

    /**
     * Check if you have a wall in front of you!
     * @param orientation the orientation we are in based on WorldSpatial
     * @param currentView what the car can currently see
     * @return
     */
    private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
        switch(orientation){
            case EAST:
                return checkEast(currentView);
            case NORTH:
                return checkNorth(currentView);
            case SOUTH:
                return checkSouth(currentView);
            case WEST:
                return checkWest(currentView);
            default:
                return false;
        }
    }

    /**
     * Check if you have a wall in front of you!
     * @param orientation the orientation we are in based on WorldSpatial
     * @param currentView what the car can currently see
     * @return
     */
    private boolean checkWallBehind(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
        switch(orientation){
            case EAST:
                return checkWest(currentView);
            case NORTH:
                return checkSouth(currentView);
            case SOUTH:
                return checkNorth(currentView);
            case WEST:
                return checkEast(currentView);
            default:
                return false;
        }
    }


    /**
     * Check if the wall is on your left hand side given your orientation
     * @param orientation
     * @param currentView
     * @return
     */
    private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {

        switch(orientation){
            case EAST:
                return checkNorth(currentView);
            case NORTH:
                return checkWest(currentView);
            case SOUTH:
                return checkEast(currentView);
            case WEST:
                return checkSouth(currentView);
            default:
                return false;
        }
    }

    /**
     * Method below just iterates through the list and check in the correct coordinates.
     * i.e. Given your current position is 10,10
     * checkEast will check up to wallSensitivity amount of tiles to the right.
     * checkWest will check up to wallSensitivity amount of tiles to the left.
     * checkNorth will check up to wallSensitivity amount of tiles to the top.
     * checkSouth will check up to wallSensitivity amount of tiles below.
     */
    public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
        // Check tiles to my right
        Coordinate currentPosition = new Coordinate(getPosition());
        for(int i = 0; i <= wallSensitivity; i++){
            MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
            if(tile.isType(MapTile.Type.WALL)){
                return true;
            }
        }
        return false;
    }

    public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
        // Check tiles to my left
        Coordinate currentPosition = new Coordinate(getPosition());
        for(int i = 0; i <= wallSensitivity; i++){
            MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
            if(tile.isType(MapTile.Type.WALL)){
                return true;
            }
        }
        return false;
    }

    public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
        // Check tiles to towards the top
        Coordinate currentPosition = new Coordinate(getPosition());
        for(int i = 0; i <= wallSensitivity; i++){
            MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
            if(tile.isType(MapTile.Type.WALL)){
                return true;
            }
        }
        return false;
    }

    public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
        // Check tiles towards the bottom
        Coordinate currentPosition = new Coordinate(getPosition());
        for(int i = 0; i <= wallSensitivity; i++){
            MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
            if(tile.isType(MapTile.Type.WALL)){
                return true;
            }
        }
        return false;
    }

    private class BFS{
        private BFS(){};
        private BFS bfs;
        private HashMap<Coordinate,Integer> dist = new HashMap<>();
        public BFS getInstance(){
            if(bfs==null){
                bfs = new BFS();
            }
            return bfs;
        }
        class Node{
            Coordinate coordinate;

            public Node(Coordinate coordinate) {
                this.coordinate = coordinate;
            }
        }

    }
}
