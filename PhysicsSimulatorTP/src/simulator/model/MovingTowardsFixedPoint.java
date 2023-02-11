package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{

	private Vector2D vectorC;
	private double acelerationG;
	
	MovingTowardsFixedPoint (Vector2D c, double g) throws IllegalArgumentException {
		if (c.equals(null) || g < 0) {
			throw new IllegalArgumentException(); 
		}
		vectorC = c;
		acelerationG = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(int i = 0; i < bs.size(); i++) {
			bs.get(i).addForce((vectorC.minus(bs.get(i).getPosition()).scale(bs.get(i).getMass()*acelerationG)));
		}
		
	}

	public String toString() {
		return "";  // TODO Auto-generated method stub
	}
}
