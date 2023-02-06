package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	public MovingBody(String i,Vector2D v,Vector2D p, double m) {
		super(i,v,p,m);
		//aqui habria q hacer try catch tb supongo
	}

	@Override
	void advance(double t) {
		// TODO Auto-generated method stub
		
	}
	void changePosition() {
		
	}
}
