package simulator.model;
import simulator.misc.Vector2D;
import org.json.*;
public abstract class Body {
	protected String id;
	protected String gId;
	protected Vector2D velocity;
	protected Vector2D fuerza;
	protected Vector2D position;
	double masa;
	
	public Body(String i, String gi, Vector2D p, Vector2D v, double m) throws IllegalArgumentException {//hacer try catch y lanzar IllegalArgumentExeption
		if (i == null || i.trim().length() <= 0 || gi == "" || gi == null || gi.trim().length()<= 0 || p == null || m <= 0 || v == null) {
			throw new IllegalArgumentException("Incorrect parameters introduced");
		}
		else {
			id=i;
			fuerza= new Vector2D();
			velocity = v;
			position=p;
			masa=m;
			gId =gi;
		}
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Body ))
			return false;
		Body other = (Body) obj;
		if (id == null || other.id == null) {
			return false;
		} else if (!id.equals(other.id)) {
			return false;
		}	
		return true;
	}
	
	public String getId() {
		return id;
	}
	public String getgId() {
		return gId;
	}
	public Vector2D getVelocity() {
		return velocity; 
	}
	public Vector2D getForce() {
		return fuerza;
	}
	public Vector2D getPosition() {
		return position;
	}
	public double getMass() {
		return masa;
	}
	void addForce(Vector2D f) {
		fuerza=fuerza.plus(f);
	}
	void resetForce() {
		fuerza=new Vector2D();
	}
	abstract void advance(double t);
	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();
		jo1.put("p", position.asJSONArray());
		jo1.put("v", velocity.asJSONArray());
		jo1.put("f", fuerza.asJSONArray());
		jo1.put("id", id);		
		jo1.put("m", masa);
		return jo1;
	}
	public String toString() {
		return getState().toString();
	}

	
}

