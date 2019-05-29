package world;

import java.util.HashMap;

import utilities.Coordinate;

public class Route {
	public static HashMap<Coordinate,Integer> route = new HashMap<Coordinate,Integer>();
	
	public static void addCoordinate(Coordinate c) {
		route.put(c, 1);
	}
	
	public static boolean checkCoordinate(Coordinate c) {
		if(route.containsKey(c))
			return true;
		else
			return false;
	}
}
