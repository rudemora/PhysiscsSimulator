package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingBody;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data.has("G")) {
			double G=data.getDouble("G");
			NewtonUniversalGravitation b=new NewtonUniversalGravitation(G);
			return b;
		}
		else {
			NewtonUniversalGravitation b=new NewtonUniversalGravitation();
			return b;
		}
	}

	
	public JSONObject getInfo() {
		JSONObject ob = new JSONObject();
		ob.put("type", getTypeTag());
		ob.put("desc", toString());
		ob.put("data", fillInData());
		return ob;
	}
	
	private String fillInData() {
		return " {\r\n"
				+ "\"G\": \"the gravitational constant (a number)\"\r\n"
				+ "}\r\n"
				+ " ";
	}
}
