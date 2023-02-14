package simulator.model;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

public class PhysicsSimulator {

	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> map;
	private double actualTime;
	private ArrayList<String> idList;
	PhysicsSimulator(double time, ForceLaws object) throws IllegalArgumentException {
		if (dt <= 0 || object == null) {
			throw new IllegalArgumentException();
		}
		else {
			time = dt;
			laws = object;
			actualTime = 0.0;
		}
	}
	
	public void advance() {
		for(BodiesGroup bg: map.values()) {
			bg.advance(dt);
		}
		actualTime += dt;
	}
	
	public void addGroup(String id) throws IllegalArgumentException {
		if (map.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		else {
			BodiesGroup bg = new BodiesGroup(id, laws);
			idList.add(id);
			map.put(id, bg);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (map.containsKey(b.getId())) {
			map.get(b.getId()).addBody(b);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) throws IllegalArgumentException {
		if (map.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		else {
			map.get(id).setForceLaws(fl);
		}
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		json.put("time", actualTime);
		for(int i = 0 ; i < idList.size(); i++) {
			json.accumulate("groups", map.get(idList.get(i)).getState());
		}
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
}


