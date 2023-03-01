package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ForceLaws createInstance(JSONObject data) {
		try {
			double g=data.getDouble("g");
			Vector2D c=new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
			ForceLaws b=new MovingTowardsFixedPoint(c,g);
			return b;
		}catch(Exception e){
			throw new IllegalArgumentException("OSASUNA NUNCA SE RINDE");
		}
	}

}
