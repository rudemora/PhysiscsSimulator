package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double G;
	
	NewtonUniversalGravitation(){
		G = 6.67E-11;
	}
	
	NewtonUniversalGravitation (double g) throws IllegalArgumentException {
		if (g > 0) {
			G = g;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void apply(List<Body> bs) {
		// TODO Auto-generated method stub
		double force = 0;
		double distance = 0;
		for(Body b1:bs) {
			for(Body b2:bs) {
				if (!b2.getPosition().equals(b1.getPosition())) {
					distance = b1.getPosition().distanceTo(b2.getPosition());
					force = G*b1.getMass()*b2.getMass() / (distance*distance);
					b1.addForce(b2.getPosition().minus(b1.getPosition()).direction().scale(force));
				}
			}
		}
	}
	
	public String toString() {
		 return ""; // TODO Auto-generated method stub
	}
}
