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
	private Map<String, BodiesGroup> mapRO;
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
			mapRO = Collections.unmodifiableMap(map);
		}
	}
	
	public void advance() {
		for(BodiesGroup bg: map.values()) {
			bg.advance(dt);
		}
		actualTime += dt;
		for(SimulatorObserver s:listObserver) {
			s.onAdvance(mapRO, actualTime);
		}
	}
	
	public void addGroup(String id) throws IllegalArgumentException {
		if (map.containsKey(id)) {
			throw new IllegalArgumentException("There's already a group with this id");
		}
		else {
			BodiesGroup bg = new BodiesGroup(id, laws);
			idList.add(id);
			map.put(id, bg);
			for(SimulatorObserver s:listObserver) {
				s.onGroupAdded(mapRO, bg);
			}
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (map.containsKey(b.getgId())) {
			map.get(b.getgId()).addBody(b);
			for(SimulatorObserver s:listObserver) {
				s.onBodyAdded(mapRO, b);
			}
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
			for(SimulatorObserver s:listObserver) {
				s.onForceLawsChanged(map.get(id));
			}
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
	
	public void reset() { 
		map.clear();
		idList.clear();
		actualTime = 0; 
		for(SimulatorObserver s:listObserver) {
			s.onReset(mapRO, actualTime, dt);
		}
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if (dt <=0) {
			throw new IllegalArgumentException("Time must be positive");
		}
		else {
			this.dt = dt;
			for(SimulatorObserver s:listObserver) {
				s.onDeltaTimeChanged(dt);
			}
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		boolean inList = false;
		for(SimulatorObserver s: listObserver) {
			if (s.equals(o)) {
				inList = true;
				o.onRegister(mapRO, actualTime, dt);
			}
		}
		if (!inList) {
			listObserver.add(o);
			o.onRegister(mapRO, actualTime, dt);
		}
		
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		for(SimulatorObserver s: listObserver) {
			if (s.equals(o)) {
				listObserver.remove(o);
				break;
			}
		}
	}
}


