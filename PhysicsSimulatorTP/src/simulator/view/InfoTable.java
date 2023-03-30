package simulator.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	// TODO cambiar el layout del panel a BorderLayout()
	JPanel pnlTable= new JPanel();
	pnlTable.setLayout(new BorderLayout());
	
	// TODO añadir un borde con título al JPanel, con el texto _title
	
	// TODO añadir un JTable (con barra de desplazamiento vertical) que use
	// _tableModel;
	
	String[]columnNames = new String[_tableModel.getColumnCount()];
	for(int i=0; i<_tableModel.getColumnCount();i++) {
		columnNames[i]=_tableModel.getColumnName(i);
	}
	Object[][] data= new Object[_tableModel.getRowCount()][_tableModel.getColumnCount()];
	for(int i=0; i<_tableModel.getRowCount();i++) {
		for(int j=0; j<_tableModel.getColumnCount(); j++) {
			data[i][j]=_tableModel.getValueAt(i, j);
		}
	}
	/*
	String[] columnNames= new String [2];
	for(int i=0; i<2;i++) {
		columnNames[i]= "columna"+i;
	}
	Object[][] data= new Object[2][2];
	for(int i=0; i<2;i++) {
		for(int j=0;j<2;j++) {
			data[i][j]="Casilla"+i+j;
		}
	}*/
	
	 JTable tbl= new JTable(data,columnNames);
	 JScrollPane scb= new JScrollPane(tbl);
	 pnlTable.add(scb);
	 
	}
	
	

}
