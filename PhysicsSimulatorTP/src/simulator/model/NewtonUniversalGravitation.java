package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double G;
	
	public NewtonUniversalGravitation(){
		G = 6.67E-11;
	}
	
	public NewtonUniversalGravitation (double g) throws IllegalArgumentException {//He puesto el public
		if (g > 0) {
			G = g;
		}
		else {
			throw new IllegalArgumentException("G must be positive");
		}
	}

	@Override
	public void apply(List<Body> bs) {
		double force = 0;
		double distance = 0;
		for(Body b1:bs) {
			if (!(b1.getMass() == 0)) {
				for(Body b2:bs) {
					if (!b2.getPosition().equals(b1.getPosition())) {
						distance = b1.getPosition().distanceTo(b2.getPosition());
						force = G*b1.getMass()*b2.getMass() / (distance*distance);
						b1.addForce(b2.getPosition().minus(b1.getPosition()).direction().scale(force));
					}
				}
			}
		}
	}
	
	public String toString() {
		 return "Newton’s Universal Gravitation with G = " + G + "."; 
	}
}
