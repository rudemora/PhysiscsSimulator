package simulator.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String _title;
	TableModel _tableModel;
	
	
	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;

		initGUI();
	}
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(_title));
		JTable tbl= new JTable(_tableModel);
		tbl.setShowGrid(false);
		JScrollPane scb= new JScrollPane(tbl);
		this.add(scb);
		
		
	}
	
}
	
	


