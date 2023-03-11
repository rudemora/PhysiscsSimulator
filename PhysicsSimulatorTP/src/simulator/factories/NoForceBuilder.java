package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.NoForce;


public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder() {
		super("nf", "ForceLaws");
	}
	
	@Override
	protected ForceLaws createInstance(JSONObject data) {
		NoForce b= new NoForce();
		return b;
		
	}
	
	
}
