package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.NoForce;


public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder() {
		super("nf", "No force");
	}
	
	@Override
	protected ForceLaws createInstance(JSONObject data) {
		NoForce b= new NoForce();
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
		return "{}";
	}
	
}
