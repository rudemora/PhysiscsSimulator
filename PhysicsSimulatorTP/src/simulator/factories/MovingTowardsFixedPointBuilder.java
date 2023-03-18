package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.NewtonUniversalGravitation;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double g=9.81;
		Vector2D c=new Vector2D();
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
		ob.put("data", fillInData());
		return ob;
	}
	
	private String fillInData() {
		return "{\r\n"
				+ "\"c\": \"the point towards which bodies move (e.g., [100.0,50.0])\",\r\n"
				+ "\"g\": \"the length of the acceleration vector (a number)\"\r\n"
				+ "}\r\n"
				+ " ";
	}

}
