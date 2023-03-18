package simulator.factories;

import java.util.Collections;
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
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> b: builders) {
			addBuilder(b);
		}
	}
	public void addBuilder(Builder<T> b) {
		
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo()); 
		
	}
	
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		T t=null;
		if(_builders.containsKey(info.getString("type"))) {
			Builder<T> b=_builders.get(info.getString("type"));
			JSONObject data= new JSONObject();
			if(info.has("data")) {
				data=info.getJSONObject("data");
			}
			t=b.createInstance(data);
			return t;
		}
		
		throw new IllegalArgumentException("Invalid value for createInstance: " +info.toString());
		
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

}
