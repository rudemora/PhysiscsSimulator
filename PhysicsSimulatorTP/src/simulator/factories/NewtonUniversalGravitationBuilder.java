package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingBody;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	private static final double G = 6.67e-11;
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double G = this.G;
		if(data.has("G")) {
			G=data.getDouble("G");
		}
		NewtonUniversalGravitation b=new NewtonUniversalGravitation(G);
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
		return " {\r\n"
				+ "\"G\": \"Gravitational constant, e.g., 6.67e-11\"\r\n"
				+ "}\r\n"
				+ " ";
	}
}
