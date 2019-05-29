package debugTest;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Test {
    public static void main(String[] args){
        TiledMap map;
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("easy-map.tmx");
    }
}
