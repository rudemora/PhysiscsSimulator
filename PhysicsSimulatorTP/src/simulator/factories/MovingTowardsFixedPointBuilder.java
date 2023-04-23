package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.NewtonUniversalGravitation;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	private static final double g = 9.81;
	private static final Vector2D c = new Vector2D();
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double g= this.g;
		Vector2D c= this.c;
		if(data.has("g")) {
			g=data.getDouble("g");
		}
		if(data.has("c")) {
			c=new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
		}
		ForceLaws b=new MovingTowardsFixedPoint(c,g);
		return b;
	}
	
	public JSONObject getInfo() {
		JSONObject ob = new JSONObject();
		ob.put("type", getTypeTag());
		ob.put("desc", toString());
		ob.put("data", new JSONObject(fillInData()));
		return ob;
	}
	
	private String fillInData() {
		return "{\r\n"
				+ "\"c\": \"A 2D vector, e.g., [1e14, 1.4e10]\",\r\n"
				+ "\"g\": \"Acceleration towards the fixpoint, e.g., 9,8\"\r\n"
				+ "}\r\n"
				+ " ";
	}

}
