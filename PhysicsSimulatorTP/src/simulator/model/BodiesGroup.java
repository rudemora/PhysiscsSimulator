package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body>{
	private String id;
	private ForceLaws laws;
	private List<Body> bodyList;
	private List<Body> bodyListRO;
	
	public BodiesGroup (String i, ForceLaws law) throws IllegalArgumentException {
		if (i == null || law == null || i.trim().length() <= 0) {
			throw new IllegalArgumentException("Incorrect parameters introduced");
		}
		else {
			id = i;
			laws = law;
			bodyList = new ArrayList<Body>(); 
			bodyListRO = Collections.unmodifiableList(bodyList);
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
					throw new IllegalArgumentException("Bodies in a group must have different ids");
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
		for(int i = 0; i < bodyList.size(); i++) {
			JSONObject repr = bodyList.get(i).getState();
			json.accumulate("bodies", repr);
		}
		json.put("id", id);
		return json;
	}
	
	public String toString() { 
		return getState().toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BodiesGroup other = (BodiesGroup) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}	
	
	public String getForceLawsInfo() {
		return laws.toString();
	}

	@Override
	public Iterator<Body> iterator() {
		return bodyListRO.iterator();
	}
}

