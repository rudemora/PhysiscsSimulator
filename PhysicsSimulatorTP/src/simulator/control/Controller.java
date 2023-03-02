package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	
	private PhysicsSimulator simulator;
	private Factory<ForceLaws> forceLaws;
	private Factory<Body> body;
	
	
	public Controller(PhysicsSimulator simulator, Factory<ForceLaws> laws, Factory<Body> body) {
		this.simulator = simulator;
		this.forceLaws = laws;
		this.body = body;
	}
	
	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray groups = jsonInput.getJSONArray("groups");
		for(int i = 0; i < groups.length();i++) {
			simulator.addGroup(groups.getString(i));
		}
		if (jsonInput.has("laws")) {
			JSONArray laws = jsonInput.getJSONArray("laws");
			for(int i = 0; i < laws.length();i++) {
				simulator.setForceLaws(laws.getJSONObject(i).getString("id"),forceLaws.createInstance(laws.getJSONObject(i).getJSONObject("laws")));
			}
		}
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < bodies.length();i++) {
			simulator.addBody(body.createInstance(bodies.getJSONObject(i)));
		}
		
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(simulator.getState());
		for(int i = 0; i < n; i++) {
			simulator.advance();
			p.println(simulator.getState()); // TODO así o con toString() ?
		}
		p.println("]");
		p.println("}");
	}
	
}
