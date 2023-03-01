55package simulator.factories;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	public BuilderBasedFactory() {
		_builders= new HashMap<String,Builder<T>>();
		_buildersInfo=new LinkedList<JSONObject>();
	}
	public BuilderBasedFactory(List<Builder<T>>) {
		this();
	}
	@Override
	public T createInstance(JSONObject info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
