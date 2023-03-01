package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;//Importado por mi
import simulator.model.Body;
import simulator.model.MovingBody;//Importado por mi

public class MovingBodyBuilder extends Builder<Body>{

	public MovingBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Body createInstance(JSONObject data) throws IllegalArgumentException{
		try {
			String id=data.getString("id");
			String gid=data.getString("gid");
			Vector2D p=new Vector2D(data.getJSONArray("p").getDouble(0),data.getJSONArray("p").getDouble(1));
			Vector2D v= new Vector2D(data.getJSONArray("v").getDouble(0),data.getJSONArray("v").getDouble(1));
			double m=data.getDouble("m");
			Body b=new MovingBody(id,gid,p,v,m);
			return b;
		}catch(Exception e){
			throw new IllegalArgumentException("OSASUNA NUNCA SE RINDE");
		}
	}

}
