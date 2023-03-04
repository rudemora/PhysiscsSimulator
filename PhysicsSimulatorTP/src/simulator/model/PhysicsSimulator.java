package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class PhysicsSimulator {

	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> map;
	private double actualTime;
	private ArrayList<String> idList;
	public PhysicsSimulator(ForceLaws object, double time) throws IllegalArgumentException {
		if (time <= 0 || object == null) {
			throw new IllegalArgumentException("Incorrect parameters introduced");
		}
		else {
			dt = time;
			laws = object;
			actualTime = 0.0;
			map = new HashMap<String, BodiesGroup>();
			idList = new ArrayList<String>();
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
			throw new IllegalArgumentException("There's already a group with this id");
		}
		else {
			BodiesGroup bg = new BodiesGroup(id, laws);
			idList.add(id);
			map.put(id, bg);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (map.containsKey(b.getgId())) {
			map.get(b.getgId()).addBody(b);
		}
		else {
			throw new IllegalArgumentException("There's not a group with this id");
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) throws IllegalArgumentException {
		if (!map.containsKey(id)) {
			throw new IllegalArgumentException("There's not a group with this id");
		}
		else {
			map.get(id).setForceLaws(fl);
		}
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		json.put("time", actualTime);
		for(int i = 0 ; i < idList.size(); i++) {
			//JSONArray array = new JSONArray();
			//array.put(map.get(idList.get(i)).getState());
			//System.out.println(array.toString());
			json.append("groups", map.get(idList.get(i)).getState());
			//json.accumulate("groups", array);//map.get(idList.get(i)).getState());
		}
		return json;
	}
	
	public String toString() { //TODO no lo utilizamos
		return getState().toString();
	}
}


