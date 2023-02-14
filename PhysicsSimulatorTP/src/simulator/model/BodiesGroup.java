package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BodiesGroup {
	private String id;
	private ForceLaws laws;
	private List<Body> bodyList;
	
	public BodiesGroup (String i, ForceLaws law) throws IllegalArgumentException {
		if (i == null || law == null || i.trim().length() > 0) {
			throw new IllegalArgumentException();
		}
		else {
			id = i;
			laws = law;
		}
	}
	
	public String getId() {
		return id;
	}
	
	void setForceLaws (ForceLaws fl) throws IllegalArgumentException {
		if (fl == null) {
			throw new IllegalArgumentException();			
		}
		else {
			laws = fl;
		}
	}
	
	void addBody(Body b) throws IllegalArgumentException {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		else {
			for(int i = 0; i < bodyList.size(); i++) {
				if (bodyList.get(i).getId() == b.getId()) {
					throw new IllegalArgumentException();
				}
			}
			bodyList.add(b);
		}
	}
	
	void advance(double dt) throws IllegalArgumentException {
		if (dt <= 0) {
			throw new IllegalArgumentException();
		}
		for(int i = 0; i < bodyList.size(); i++) {
			bodyList.get(i).resetForce();
		}
		laws.apply(bodyList);
		for(int i = 0; i < bodyList.size(); i++) {
			bodyList.get(i).advance(dt);
		}
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		for(int i = 0; i < bodyList.size(); i++) {
			json.accumulate("bodies", bodyList.get(i).getState());
		}
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
}
