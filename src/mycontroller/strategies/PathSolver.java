package mycontroller.strategies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;


import tiles.MapTile;
import utilities.Coordinate;

public class PathSolver {

	private LinkedList<Coordinate> returnRoute;
	private Stack<Coordinate> traversal;
	private Coordinate currentPosition;
	private Set<Coordinate> markedPoints;
	private Stack<Coordinate> route;
	public LinkedList<Coordinate> exitRouter;
	private HashMap<Coordinate,MapTile> mapTiles;
	private boolean isBack;
	private Coordinate backPoint;
	private Coordinate exitPoint;

	public PathSolver(HashMap<Coordinate,MapTile> mapTiles,String Position) {
		Coordinate initialPosition = new Coordinate(Position);
		initialPosition.x++;
		returnRoute = new LinkedList<>();
		traversal = new Stack<>();
		markedPoints = new HashSet<>();
		route = new Stack<>();
		this.mapTiles = mapTiles;
		isBack = true;
		backPoint = initialPosition;
		exitPoint = new Coordinate(initialPosition.x,initialPosition.y);

		traversal.add(initialPosition);
		returnRoute.add(initialPosition);
		currentPosition = initialPosition;
	}

	public LinkedList<Coordinate> start(){

		while(!traversal.isEmpty()) {


			//Record the shortest way to exit
			if(mapTiles.get(currentPosition).getType() == MapTile.Type.FINISH) {
				exitPoint = currentPosition;
			}

			Coordinate eastPoint = nextPoint("east");
			Coordinate westPoint = nextPoint("west");
			Coordinate southPoint = nextPoint("south");
			Coordinate northPoint = nextPoint("north");

			if(checkNextPoint(eastPoint)) {	//Check the eastern point is road or finish

				if(backPoint.equals(currentPosition)) {
					route = new BFS(currentPosition,eastPoint,mapTiles).getPath();
				}else {
					route = new BFS(backPoint,eastPoint,mapTiles).getPath();
				}

				goNextPoint(eastPoint);

			}else if(checkNextPoint(westPoint)) {	//Check the western point is road or finish

				if(backPoint.equals(currentPosition)) {
					route = new BFS(currentPosition,westPoint,mapTiles).getPath();
				}else {
					route = new BFS(backPoint,westPoint,mapTiles).getPath();
				}

				goNextPoint(westPoint);

			}else if(checkNextPoint(southPoint)) {	//Check the southern point is road or finish



				if(backPoint.equals(currentPosition)) {
					route = new BFS(currentPosition,southPoint,mapTiles).getPath();
				}else {
					route = new BFS(backPoint,southPoint,mapTiles).getPath();
				}

				goNextPoint(southPoint);

			}else if(checkNextPoint(northPoint)) {	//Check the northern point is road or finish



				if(backPoint.equals(currentPosition)) {
					route = new BFS(currentPosition,northPoint,mapTiles).getPath();
				}else {
					route = new BFS(backPoint,northPoint,mapTiles).getPath();
				}

				goNextPoint(northPoint);

			}else {

				if(isBack) {
					isBack = false;
					backPoint = currentPosition;
				}

				currentPosition = traversal.pop();	//Track back


			}
		}
		route = new BFS(returnRoute.getLast(),exitPoint,mapTiles).getPath();
		returnRoute.removeLast();
		mergePath();

		returnRoute.poll();
		return returnRoute;
	}

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

	private boolean checkNextPoint(Coordinate nextPoint) {
		if(nextPoint != null && (!markedPoints.contains(nextPoint)) &&
				(mapTiles.get(nextPoint).getType() == MapTile.Type.ROAD ||
						mapTiles.get(nextPoint).getType() == MapTile.Type.FINISH)) {
			return true;
		}else {
			return false;
		}
	}


	private void mergePath() {

		while(!route.isEmpty()) {
			returnRoute.add(route.pop());

		}
	}

	private void goNextPoint(Coordinate nextPoint) {
		mergePath();

		markedPoints.add(nextPoint);

		traversal.add(nextPoint);

		currentPosition = nextPoint;

		backPoint = currentPosition;

		isBack = true;
	}
}
