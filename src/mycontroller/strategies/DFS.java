package mycontroller.strategies;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.*;

public class DFS {
    private Coordinate initialPosition;
    private Set<Coordinate> markedPoints;
    public LinkedList<Coordinate> router;
    private Stack<Coordinate> retrival;
    private Coordinate currentPosition;
    public LinkedList<Coordinate> exitRouter;

    public DFS(String initialPosition) {

        this.initialPosition = new Coordinate(initialPosition);	//The next point of initial position of car
        retrival = new Stack<Coordinate>();
        markedPoints = new HashSet<>();
        router = new LinkedList<>();
        markedPoints.add(this.initialPosition);
        retrival.push(this.initialPosition);
        router.add(this.initialPosition);
        currentPosition = this.initialPosition;
        exitRouter = new LinkedList<Coordinate>();
    }

    public LinkedList<Coordinate> start(HashMap<Coordinate,MapTile> mapTiles){

        while(!retrival.isEmpty()) {

            //Record the shortest way to exit
            if(mapTiles.get(currentPosition).getType() == MapTile.Type.FINISH) {
                exitRouter = (LinkedList<Coordinate>)router.clone();
            }

            //Generate the neighbors of current point
            Coordinate eastPoint = nextPoint("east");
            Coordinate westPoint = nextPoint("west");
            Coordinate southPoint = nextPoint("south");
            Coordinate northPoint = nextPoint("north");

            if(checkNextPoint(mapTiles, eastPoint)) {	//Check the eastern point is road or finish

                if(!retrival.peek().equals(currentPosition)) {
                    retrival.push(currentPosition);
                }

                goNextPoint(eastPoint);

            }else if(checkNextPoint(mapTiles, westPoint)) {	//Check the western point is road or finish

                if(!retrival.peek().equals(currentPosition)) {
                    retrival.push(currentPosition);
                }

                goNextPoint(westPoint);

            }else if(checkNextPoint(mapTiles, southPoint)) {	//Check the southern point is road or finish

                if(!retrival.peek().equals(currentPosition)) {
                    retrival.push(currentPosition);
                }

                goNextPoint(southPoint);

            }else if(checkNextPoint(mapTiles, northPoint)) {	//Check the northern point is road or finish

                if(!retrival.peek().equals(currentPosition)) {
                    retrival.push(currentPosition);
                }

                goNextPoint(northPoint);

            }else {
                if(!router.getLast().equals(currentPosition)) {
                    router.add(currentPosition);
                }

                currentPosition = retrival.pop();	//Track back
                router.add(currentPosition);

            }
        }

        return router;
    }

    //Generate the neighbors of current point
    private Coordinate nextPoint(String orientation) {
        switch(orientation) {
            case "east":
                return new Coordinate(currentPosition.x+1,currentPosition.y);
            case "west":
                if(currentPosition.x!=0) {
                    return new Coordinate(currentPosition.x-1,currentPosition.y);
                }else {
                    return null;
                }
            case "south":
                if(currentPosition.y!=0) {
                    return new Coordinate(currentPosition.x,currentPosition.y-1);
                }else {
                    return null;
                }
            case "north":
                return new Coordinate(currentPosition.x,currentPosition.y+1);
        }

        return null;
    }

    //Check the next point is road or finish
    private boolean checkNextPoint(HashMap<Coordinate,MapTile> mapTiles,Coordinate nextPoint) {
        if(nextPoint != null && (!markedPoints.contains(nextPoint)) &&
                (mapTiles.get(nextPoint).getType() == MapTile.Type.ROAD ||
                        mapTiles.get(nextPoint).getType() == MapTile.Type.FINISH)) {
            return true;
        }else {
            return false;
        }
    }

    //Go to the next point
    private void goNextPoint(Coordinate nextPoint) {
        markedPoints.add(nextPoint);
        retrival.push(nextPoint);
        router.add(nextPoint);
        currentPosition = nextPoint;
    }
}
