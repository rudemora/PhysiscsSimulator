package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	//private List<BodiesGroup> _groupsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private JButton _ok;
	private JButton _cancel;
	private boolean _status;
	// TODO en caso de ser necesario, añadir los atributos aquí…
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// _forceLawsInfo se usará para establecer la información en la tabla
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==1) {
					return true;
				}
				return false;
			}
		};
			
		JTable tbl= new JTable(_dataTableModel);
		JScrollPane scb= new JScrollPane(tbl);
		mainPanel.add(scb);
		
		_dataTableModel.setColumnIdentifiers(_headers);
		_lawsModel = new DefaultComboBoxModel<>();
		// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
		for(JSONObject j: _forceLawsInfo ) {
			_lawsModel.addElement(j.getString("type"));
		}
		// TODO crear un combobox que use _lawsModel y añadirlo al panel
		
		
		// TODO crear un combobox que use _groupsModel y añadirlo al panel
		_groupsModel = new DefaultComboBoxModel<>();
		
		
		// TODO crear los botones OK y Cancel y añadirlos al panel
		_ok= new JButton();
		_ok.addActionListener((e)->ok());
		mainPanel.add(_ok);
		_cancel= new JButton();
		_cancel.addActionListener((e)->cancel());
		mainPanel.add(_cancel);
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	private void cancel() {
		setVisible(false);
		
	}
	private void ok() {
		//ahora falta actualizar los cambios en el grupo
		setVisible(false);
	}
	public boolean open() {
		
	//if (_groupsModel.getSize() == 0) //Es necesario comentarlo pq al ppio siempre pasa pq no estamos metiendo nada y nunca se pone set visible
		//return _status;// no se que pollas hace
	// TODO Establecer la posición de la ventana de diálogo de tal manera que se
	// abra en el centro de la ventana principal
		add(this,BorderLayout.CENTER);//TODO tengo dudas
		pack();
		setVisible(true);
		return _status;
	}
	// TODO el resto de métodos van aquí…
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		//_groupsInfo.add(g);
		_groupsModel.addElement(g.getId());
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		
		
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