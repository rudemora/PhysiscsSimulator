package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingBody;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "ForceLaws");
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

}
