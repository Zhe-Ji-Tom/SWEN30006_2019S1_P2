package mycontroller.strategies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import tiles.MapTile;
import utilities.Coordinate;

public class BFS {
	private Stack<Coordinate> route;
	private LinkedList<Coordinate> queue;
	private HashMap<Coordinate,Integer> markedPoints;
	private Coordinate currentPosition;
	private Coordinate startPoint;
	private Coordinate endPoint;
	private HashMap<Coordinate,MapTile> mapTiles;
	private int layer;
	
	
	public BFS(Coordinate startPoint,Coordinate endPoint,HashMap<Coordinate,MapTile> mapTiles) {
		layer = 1;
		route = new Stack<>();
		queue = new LinkedList<>();
		markedPoints = new HashMap<>();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.mapTiles = mapTiles;
		
		currentPosition = new Coordinate(startPoint.x,startPoint.y);
		queue.add(currentPosition);
		markedPoints.put(currentPosition, layer);
		
	}
	
	
	
	public Stack<Coordinate> getPath(){
		
		while(!queue.isEmpty()) {
			
			currentPosition = queue.poll();
			
			layer = markedPoints.get(currentPosition)+1;
			
			if(currentPosition.equals(endPoint)) {
				producePath(currentPosition);
				
				return route;
			}
			
			Coordinate eastPoint = nextPoint(currentPosition,"east");
			Coordinate westPoint = nextPoint(currentPosition,"west");
			Coordinate southPoint = nextPoint(currentPosition,"south");
			Coordinate northPoint = nextPoint(currentPosition,"north");
			
			if(checkNextPoint(eastPoint)) {
				addPoint(eastPoint);
			}
			
			if(checkNextPoint(westPoint)) {
				addPoint(westPoint);
			}
			
			if(checkNextPoint(southPoint)) {
				addPoint(southPoint);
			}
			
			if(checkNextPoint(northPoint)) {
				addPoint(northPoint);
			}
			
		}
		
		return null;
	}
	
	private Coordinate nextPoint(Coordinate currentPosition,String orientation) {
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
		if(nextPoint != null && (!markedPoints.containsKey(nextPoint)) && 
				(mapTiles.get(nextPoint).getType() == MapTile.Type.ROAD || 
				 mapTiles.get(nextPoint).getType() == MapTile.Type.FINISH)) {
			return true;
		}else {
			return false;
		}
	}
	
	private void addPoint(Coordinate nextPoint) {
		queue.add(nextPoint);
		markedPoints.put(nextPoint,layer);
	}
	
	private void producePath(Coordinate currentCoordinate) {
		Coordinate curPoint = currentCoordinate;
		while(!curPoint.equals(startPoint)) {
			curPoint = findPrevious(curPoint);
			route.push(curPoint);
		}
	}
	
	private Coordinate findPrevious(Coordinate curPoint) {
		Coordinate eastPoint = nextPoint(curPoint,"east");
		Coordinate westPoint = nextPoint(curPoint,"west");
		Coordinate southPoint = nextPoint(curPoint,"south");
		Coordinate northPoint = nextPoint(curPoint,"north");
		
		if(markedPoints.containsKey(eastPoint) && 
				(markedPoints.get(eastPoint) == markedPoints.get(curPoint)-1)) {
			return eastPoint;
		}else if(markedPoints.containsKey(westPoint) && 
				(markedPoints.get(westPoint) == markedPoints.get(curPoint)-1)) {
			return westPoint;
		}else if(markedPoints.containsKey(southPoint) && 
				(markedPoints.get(southPoint) == markedPoints.get(curPoint)-1)) {
			return southPoint;
		}else if(markedPoints.containsKey(northPoint) && 
				(markedPoints.get(northPoint) == markedPoints.get(curPoint)-1)) {
			return northPoint;
		}
		
		return null;
	}
}
