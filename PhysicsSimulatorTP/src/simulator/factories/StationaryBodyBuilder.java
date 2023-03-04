package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder() {
		super("st_body","Body");
	}
	

	@Override
	protected Body createInstance(JSONObject data) {
		try {
			String id=data.getString("id");
			String gid=data.getString("gid");
			if(data.getJSONArray("p").length()>2) {
				throw new IllegalArgumentException();
			}
			Vector2D p=new Vector2D(data.getJSONArray("p").getDouble(0),data.getJSONArray("p").getDouble(1));
			double m=data.getDouble("m");
			Body b=new StationaryBody(id,gid,p,m);
			return b;
		}catch(Exception e){
			throw new IllegalArgumentException();
		}

	}

}
