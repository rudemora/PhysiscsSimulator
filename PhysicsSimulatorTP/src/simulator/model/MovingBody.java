package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	public MovingBody(String id, String gId, Vector2D p, Vector2D v,double m) {
		super(id, gId, p,v,m);
	}

	@Override
	void advance(double t) {
		Vector2D aceleration;
		if (masa == 0) {
			aceleration = new Vector2D(0,0);
		}
		else {
			aceleration = fuerza.scale(1/masa);
		}
		position = position.plus(velocity.scale(t).plus(aceleration.scale(0.5*(t*t))));
		velocity = velocity.plus(aceleration.scale(t));
	}
	
}
