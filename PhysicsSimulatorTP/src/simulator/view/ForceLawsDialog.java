package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private JComboBox<String> _comboLaws;
	private JComboBox<String> _comboGroups;
	private DefaultTableModel _dataTableModel;
	private JTable tbl;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private List<BodiesGroup> _groupsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private JButton _ok;
	private JButton _cancel;
	private boolean _status;
	int _selectedLawsIndex;
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
		
	}
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// _forceLawsInfo se usará para establecer la información en la tabla
		// help
		JLabel help = new JLabel("<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		_dataTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==1) {
					return true;
				}
				return false;
			}
		};
		_dataTableModel.setColumnIdentifiers(_headers);
	
		tbl= new JTable(_dataTableModel);
		JScrollPane scb= new JScrollPane(tbl);
		mainPanel.add(scb, BorderLayout.NORTH);
		_lawsModel = new DefaultComboBoxModel<>();
		
		for(JSONObject j: _forceLawsInfo ) {
			_lawsModel.addElement(j.getString("desc"));
		}
		JPanel panel1 = new JPanel(new FlowLayout());
		JPanel panel2 = new JPanel(new FlowLayout());
		JLabel label1 = new JLabel("Force law: ");
		JLabel label2 = new JLabel("Group: ");
		_comboLaws= new JComboBox<String> (_lawsModel);
		panel1.add(label1);
		panel1.add(new JSeparator(JSeparator.VERTICAL));
		panel1.add(_comboLaws);
		_comboLaws.addActionListener((e)->selectedLaw());	
		panel1.add(new JSeparator(JSeparator.VERTICAL));
		panel1.add(label2);
		panel1.add(new JSeparator(JSeparator.VERTICAL));
		panel1.add(_comboGroups);
		_cancel= new JButton("Cancel");
		_cancel.addActionListener((e)->cancel());
		panel2.add(_cancel);
		panel2.add(new JSeparator(JSeparator.VERTICAL));
		_ok= new JButton("Ok");
		_ok.addActionListener((e)->ok());
		panel2.add(_ok);
		

		mainPanel.add(panel1, BorderLayout.CENTER);
		mainPanel.add(panel2, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	private void selectedLaw() {
		_selectedLawsIndex= _comboLaws.getSelectedIndex();
		JSONObject info= _forceLawsInfo.get(_selectedLawsIndex);
		JSONObject data= info.getJSONObject("data");
		int j = 0;
		int tamano = _dataTableModel.getRowCount();
		for(j=0; j<tamano;j++) {
			_dataTableModel.removeRow(0);
			
		}
		
		int k=0;
		for(String key: data.keySet()) {
			String[] row= {key,"",data.getString(key)};
			_dataTableModel.addRow(row);
			k++;
			
		}
		_dataTableModel.fireTableRowsInserted(0,k);
		
	}
	private void cancel() {
		setVisible(false);
		_status=false;
		
	}
	private void ok() {
		try {
			JSONObject fl = new JSONObject("{\"type\":" + _forceLawsInfo.get(_selectedLawsIndex).getString("type")
					+ ", \"data\":" + getJSON() + "}");
			_groupsInfo.get(_comboGroups.getSelectedIndex()).getId();
			_ctrl.setForceLaws(_groupsInfo.get(_comboGroups.getSelectedIndex()).getId(), fl);
			_status=true;
			setVisible(false);
		}catch(Exception e) {
			Utils.showErrorMsg("Error en ForceLawsDialog");
		}
		
	}
	public boolean open() {
		
	if (_groupsModel.getSize() == 0) 

		pack();
		_status=true;
		setVisible(true);
		return _status;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_comboGroups.removeAll();
	}
	@Override 
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel = new DefaultComboBoxModel<>();
		_groupsInfo = new ArrayList<BodiesGroup>();
		_comboGroups= new JComboBox<String> (_groupsModel);		
		for(BodiesGroup b: groups.values()) {
			_comboGroups.addItem(b.getId());
			_groupsInfo.add(b);
		}		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsInfo.add(g);
		_groupsModel.addElement(g.getId());		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
				
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		
	}
	
	private String getJSON() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String k = _dataTableModel.getValueAt(i, 0).toString();
			String v = _dataTableModel.getValueAt(i, 1).toString();
			if (!v.isEmpty()) {
				s.append('"');
				s.append(k);
				s.append('"');
				s.append(':');
				s.append(v);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		return s.toString();
	}
}