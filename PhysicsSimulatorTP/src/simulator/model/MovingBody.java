package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	public MovingBody(String id, String gId, Vector2D v,Vector2D p, double m) {
		super(id, gId, v,p,m);
		//aqui habria q hacer try catch tb supongo
	}

	@Override
	void advance(double t) {
		// TODO Auto-generated method stub
		
		Vector2D aceleration = fuerza.scale(1/masa);
		position = position.plus(velocity.scale(t));
		position = position.plus(aceleration.scale(1/2*(t*t)));
	}
	void changePosition() {
		
	}
}
