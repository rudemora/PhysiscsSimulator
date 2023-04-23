package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	private Controller _ctrl;
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		_ctrl=ctrl;
		_ctrl.addObserver(this);
	}


	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public String getColumnName(int col) {
		return _header[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: 
			return _bodies.get(rowIndex).getId();
		case 1: 
			return _bodies.get(rowIndex).getgId();
		case 2:
			return _bodies.get(rowIndex).getMass();
		case 3:
			return _bodies.get(rowIndex).getVelocity();
		case 4:
			return _bodies.get(rowIndex).getPosition();
		case 5:
			return _bodies.get(rowIndex).getForce();
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		fireTableDataChanged();
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		fireTableStructureChanged();
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		
		_bodies.clear();
		for(BodiesGroup bg:groups.values()) {
			for(Body b: bg) {
				_bodies.add(b);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b:g) {
			_bodies.add(b);
		}
		fireTableStructureChanged();
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		fireTableStructureChanged();
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		
		
	}

}
