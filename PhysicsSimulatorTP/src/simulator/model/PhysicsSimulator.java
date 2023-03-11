package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class PhysicsSimulator implements Observable<SimulatorObserver>{

	private double dt;
	private ForceLaws laws;
	private Map<String, BodiesGroup> map;
	private double actualTime;
	private ArrayList<String> idList;
	private ArrayList<SimulatorObserver> listObserver;
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
			listObserver = new ArrayList<SimulatorObserver>();
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
			json.append("groups", map.get(idList.get(i)).getState());
		}
		return json;
	}
	
	public String toString() { 
		return getState().toString();
	}
	
	public void reset() { //TODO dice en el enunciado con el metodo clear??
		map = new HashMap<String, BodiesGroup>();
		idList = new ArrayList<String>();
		dt = 0; 
		actualTime = 0; //TODO he puesto los dos a 0 pero no se si solo hay que poner este
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if (dt <=0) {
			throw new IllegalArgumentException("Time must be positive");
		}
		else {
			this.dt = dt;
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		boolean inList = false;
		for(SimulatorObserver s: listObserver) {
			if (s.equals(o)) {
				inList = true;
			}
		}
		if (!inList) {
			listObserver.add(o);
		}
		
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		boolean inList = false;
		for(SimulatorObserver s: listObserver) {
			if (s.equals(o)) {
				inList = true;
			}
		}
		if (inList) {
			listObserver.remove(o);
		}
	}
}


