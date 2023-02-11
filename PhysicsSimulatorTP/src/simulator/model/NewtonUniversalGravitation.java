package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double G;
	
	NewtonUniversalGravitation (double g) throws IllegalArgumentException {
		if (G >= 0) {
			G = g;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void apply(List<Body> bs) {
		// TODO Auto-generated method stub
		for (int i = 0; i < bs.size(); i++) {
			double force = 0;
			if (bs.get(i).getMass() != 0) {
				for(int j = 0; j < bs.size(); j++) {
					if (i!=j) {
						double magnitude = bs.get(j).getPosition().minus(bs.get(i).getPosition()).magnitude();
						if (magnitude != 0) {
							force = (G*bs.get(i).getMass()*bs.get(j).getMass()) / (magnitude);
							bs.get(i).addForce(bs.get(j).getPosition().minus(bs.get(i).getPosition()).scale(force));
						}
					}
				}				
			
			//force = G*bs.get(i).getMass()*force; // este factor común funciona
			}
		}
	}
	
	public String toString() {
		 return ""; // TODO Auto-generated method stub
	}
}
