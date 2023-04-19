package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	private Controller _ctrl;
	GroupsTableModel(Controller ctrl) {
		_groups = new ArrayList<>();
		_ctrl=ctrl;
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _groups.size();
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
			return _groups.get(rowIndex).getId();
		case 1: 
			return _groups.get(rowIndex).getForceLawsInfo();
		case 2:
			String st = " ";
			for(Body b: _groups.get(rowIndex)) {
				st+=b.getId() + " ";
			}
			return st;
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		fireTableStructureChanged();
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		for(BodiesGroup b: groups.values()) {
			_groups.add(b);
		}
		fireTableStructureChanged();
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groups.add(g);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_groups.clear();
		for(BodiesGroup b1: groups.values()) {
			_groups.add(b1);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {	
		fireTableStructureChanged();
	}
	
	

}
