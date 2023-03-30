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
	//private static final long serialVersionUID = 1L;
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	private Controller _ctrl;
	GroupsTableModel(Controller ctrl) {
	_groups = new ArrayList<>();
	_ctrl=ctrl;
	_ctrl.addObserver(this);
	// TODO registrar this como observador;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	
	

}
