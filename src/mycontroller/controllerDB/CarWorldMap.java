package mycontroller.controllerDB;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.*;

public class CarWorldMap {
    public HashMap<Coordinate, MapTile> getWorld() {
        return world;
    }
    private ArrayList<Coordinate> finish;
    private HashMap<Coordinate, MapTile> world;
    private CarWorldMap(){
        path = new LinkedList<>();
        finish = new ArrayList<>();
        parcelSets = new ArrayList<>();
    }
    private static CarWorldMap carWorldMap;
    private Queue<Coordinate> path;

    private ArrayList<Coordinate> parcelSets;

    public Queue<Coordinate> getExitPath() {
        return exitPath;
    }

    public void setExitPath(Queue<Coordinate> exitPath) {
        this.exitPath = exitPath;
    }

    private Queue<Coordinate> exitPath;

    public void addPoint(Coordinate point){
        path.add(point);
    }

    public void newPath(Queue newPath){
        path = newPath;
    }

    public Coordinate getPoint(){
        if(path.isEmpty()){
            return null;
        }else {
            return path.poll();
        }
    }

    public static CarWorldMap getInstance(){
        if(carWorldMap==null){
            carWorldMap = new CarWorldMap();
        }
        return carWorldMap;
    }
    //Have to be initial at Concrete Controller!
    public void build(HashMap<Coordinate, MapTile> world){
        this.world = world;
        world.forEach((key,value)->{
            if(value.isType(MapTile.Type.FINISH)){
                finish.add(key);
            }
        });
    }
    //Refresh world according to currentView
    public void refreshMap(HashMap<Coordinate,MapTile> currentView){
        //lambda expression
        currentView.forEach((key,value)-> world.replace(key,value));
    }

    public int getNumParcels(){
        return parcelSets.size();
    }
}
