package tiles;

import world.Car;

public abstract class TrapTile extends MapTile{

	public TrapTile() {
		super(Type.TRAP);
	}
	
	public abstract String getTrap();
	
	public abstract void applyTo(Car car, float delta);
	
	public abstract boolean canAccelerate();
	
	public abstract boolean canTurn();

}
