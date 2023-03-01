package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingBody;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		try {
			double G=data.getDouble("G");
			ForceLaws b=new NewtonUniversalGravitation(G);
			return b;
		}catch(Exception e){
			throw new IllegalArgumentException("OSASUNA NUNCA SE RINDE");
		}
	}

}
