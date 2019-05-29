package mycontroller.strategies;

import utilities.Coordinate;
import world.WorldSpatial;

public class AbstractAutoControllerStrategy{

    public String orientation(WorldSpatial.Direction current, WorldSpatial.Direction destination){
        if(current == WorldSpatial.Direction.NORTH){
            if(destination== WorldSpatial.Direction.EAST){
                return "right";
            }
            if(destination== WorldSpatial.Direction.WEST){
                return "left";
            }
            if(destination== WorldSpatial.Direction.SOUTH){
                return "reverse";
            }
        }
        if(current== WorldSpatial.Direction.EAST){
            if(destination== WorldSpatial.Direction.SOUTH){
                return "right";
            }
            if(destination== WorldSpatial.Direction.NORTH){
                return "left";
            }
            if(destination== WorldSpatial.Direction.WEST){
                return "reverse";
            }

        }
        if(current== WorldSpatial.Direction.SOUTH){

            if(destination== WorldSpatial.Direction.WEST){
                return "right";
            }
            if(destination== WorldSpatial.Direction.EAST){
                return "left";
            }
            if(destination== WorldSpatial.Direction.NORTH){
                return "reverse";
            }

        }
        if(current== WorldSpatial.Direction.WEST){
            if(destination== WorldSpatial.Direction.NORTH){
                return "right";
            }
            if(destination== WorldSpatial.Direction.SOUTH){
                return "left";
            }
            if(destination== WorldSpatial.Direction.EAST){
                return "reverse";
            }

        }
        return "forward";
    }

}
